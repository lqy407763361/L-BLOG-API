package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.JwtTokenUtil;
import com.lblog.common.util.SHA256Util;
import com.lblog.common.util.PageResultUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.common.validation.FormValidation;
import com.lblog.dao.AdminDao;
import com.lblog.dao.AdminLoginRecordDao;
import com.lblog.dao.AdminRefreshTokenDao;
import com.lblog.domain.*;
import com.lblog.dto.AdminDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminLoginRecordDao adminLoginRecordDao;

    @Autowired
    private AdminRefreshTokenDao adminRefreshTokenDao;

    //登录
    @Transactional
    public Map<String, Object> login(Admin admin, String adminIp){
        //判断管理员是否存在
        String account = admin.getAccount();
        Long adminId = adminDao.getAdminId(account);
        if((adminId == null) || (adminId == 0)){
            throw new ReturnException("管理员不存在！");
        }

        //判断密码是否正确
        String rawPassword = "";
        String password = admin.getPassword();
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ReturnException("密码解密失败！");
        }
        String salt = admin.getSalt();
        if(!SHA256Util.getEncrypt(rawPassword, salt).equals(this.getAdminDetail(adminId).getPassword())){
            throw new ReturnException("密码错误！");
        }

        //判断管理员账号状态
        Integer status = admin.getStatus();
        if(status == 2){
            throw new ReturnException("管理员已被禁用！");
        }

        //插入登录记录表
        Long addTime = Instant.now().getEpochSecond();
        AdminLoginRecord adminLoginRecord = new AdminLoginRecord();
        adminLoginRecord.setAdminId(adminId);
        adminLoginRecord.setLoginIp(adminIp);
        adminLoginRecord.setLoginTime(addTime);
        adminLoginRecordDao.addAdminLoginRecord(adminLoginRecord);

        //保存refreshToken，返回accessToken/refreshToken
        PrivateKey privateKey = RSAUtil.getPrivateKey();
        String accessToken = JwtTokenUtil.generateAccessToken(adminId, privateKey);
        String refreshToken = JwtTokenUtil.generateRefreshToken(adminId, privateKey);
        AdminRefreshToken adminRefreshToken = new AdminRefreshToken();
        adminRefreshToken.setAdminId(adminId);
        adminRefreshToken.setRefreshToken(refreshToken);
        adminRefreshToken.setIsRevoked(0);
        adminRefreshToken.setAddTime(addTime);
        adminRefreshTokenDao.addAdminRefreshToken(adminRefreshToken);

        String name = this.getAdminDetail(adminId).getName();
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    //添加
    @Transactional
    public void addAdmin(Admin admin){
        //判断账号格式是否合法和是否已存在
        String account = "";
        if(StringUtils.isBlank(admin.getAccount())){
            throw new ReturnException("账号不能为空！");
        }
        account = admin.getAccount().trim();
        Long existAdminId = adminDao.getAdminId(account);
        if((existAdminId != null) && (existAdminId > 0)){
            throw new ReturnException("管理员已存在！");
        }

        //判断所属群组ID
        Long adminGroupId = admin.getGroupId();
        if((adminGroupId == null) || (adminGroupId == 0)){
            throw new ReturnException("管理员群组ID不能为空！");
        }

        //判断密码格式是否合法
        if(!FormValidation.passwordValidation(admin.getPassword())){
            throw new ReturnException("密码格式错误！");
        }
        String password = admin.getPassword().trim();

        //判断名称格式是否合法
        if(StringUtils.isBlank(admin.getName())){
            throw new ReturnException("账号不能为空！");
        }
        String name = admin.getName().trim();

        //内容过滤
        String description = "";
        if(!StringUtils.isBlank(admin.getDescription())){
            description = admin.getDescription().trim();
        }

        Long addTime = Instant.now().getEpochSecond();
        String salt = addTime + SHA256Util.RandomString(8);
        password = SHA256Util.getEncrypt(password, salt);
        admin.setGroupId(adminGroupId);
        admin.setAccount(account);
        admin.setName(name);
        admin.setPassword(password);
        admin.setSalt(salt);
        admin.setDescription(description);
        admin.setStatus(1);
        admin.setAddTime(addTime);
        Integer returnRow = adminDao.addAdmin(admin);
        if((returnRow == null) || (returnRow == 0)){
            throw new ReturnException("添加失败！");
        }
    }

    //退出登录
    @Transactional
    public void loginOut(Long adminId, String refreshToken){
        //判断管理员ID
        if((adminId == null) || (adminId == 0)){
            throw new ReturnException("用户ID不能为空！");
        }

        //判断refreshToken
        if(StringUtils.isBlank(refreshToken)){
            throw new ReturnException("refreshToken不能为空！");
        }
 
        //判断refreshToken
        AdminRefreshToken adminRefreshToken = new AdminRefreshToken();
        adminRefreshToken.setAdminId(adminId);
        adminRefreshToken.setRefreshToken(refreshToken);
        Long refreshTokenId = adminRefreshTokenDao.getAdminRefreshTokenId(adminRefreshToken);
        if((refreshTokenId == null) || (refreshTokenId == 0)){
            throw new ReturnException("refreshToken不存在！");
        }

        adminRefreshTokenDao.deleteAdminRefreshToken(adminRefreshToken);
    }

    //刷新token
    @Transactional
    public String refreshAccessToken(Long adminId, String refreshToken){
        //判断用户ID
        if((adminId == null) || (adminId == 0)){
            throw new ReturnException("管理员ID不能为空！");
        }

        //判断refreshToken
        AdminRefreshToken adminRefreshToken = new AdminRefreshToken();
        adminRefreshToken.setAdminId(adminId);
        adminRefreshToken.setRefreshToken(refreshToken);
        Long refreshTokenId = adminRefreshTokenDao.getAdminRefreshTokenId(adminRefreshToken);
        if((refreshTokenId == null) || (refreshTokenId == 0)){
            throw new ReturnException("refreshToken不存在！");
        }

        //验证refreshToken合法性
        PublicKey publicKey = RSAUtil.getPublicKey();
        JwtTokenUtil.validateToken(refreshToken, publicKey);
        PrivateKey privateKey = RSAUtil.getPrivateKey();
        String accessToken = JwtTokenUtil.generateAccessToken(adminId, privateKey);

        return accessToken;
    }

    //编辑用户
    @Transactional
    public void editAdmin(Admin admin){
        //判断管理员是否存在
        Long adminId = admin.getId();
        Admin adminDetail = this.getAdminDetail(adminId);
        if(adminDetail == null){
            throw new ReturnException("管理员不存在！");
        }

        //判断所属群组ID
        Long adminGroupId = admin.getGroupId();
        if((adminGroupId == null) || (adminGroupId == 0)){
            throw new ReturnException("管理员群组ID不能为空！");
        }

        //判断密码格式是否合法
        if(!FormValidation.passwordValidation(admin.getPassword())){
            throw new ReturnException("密码格式错误！");
        }
        String password = admin.getPassword().trim();

        //判断名称格式是否合法
        if(StringUtils.isBlank(admin.getName())){
            throw new ReturnException("账号不能为空！");
        }
        String name = admin.getName().trim();

        //内容过滤
        String description = "";
        if(!StringUtils.isBlank(admin.getDescription())){
            description = admin.getDescription().trim();
        }

        Long editTime = Instant.now().getEpochSecond();
        String salt = editTime + SHA256Util.RandomString(8);
        password = SHA256Util.getEncrypt(password, salt);
        Integer status = admin.getStatus();
        admin.setGroupId(adminGroupId);
        admin.setName(name);
        admin.setPassword(password);
        admin.setSalt(salt);
        admin.setDescription(description);
        admin.setStatus(status);
        admin.setEditTime(editTime);
        Integer returnRow = adminDao.editAdmin(admin);
        if((returnRow == null) || (returnRow == 0)){
            throw new ReturnException("编辑失败！");
        }
    }

    //删除管理员
    @Transactional
    public void deleteAdmin(Map<String, List<Long>> adminId){
        //判断管理员ID
        if((adminId == null) || adminId.isEmpty()){
            throw new ReturnException("管理员ID不能为空！");
        }

        List<Long> adminIdList = adminId.get("id");
        adminDao.deleteAdmin(adminIdList);
    }

    //获取管理员列表
    public PageResultUtil<AdminDto> getAdminList(Integer page, Integer size, Admin admin, AdminGroup adminGroup){
        //起始位置
        Integer startNum = (page-1) * size;
        //获取总数
        Integer total = adminDao.getAdminTotal(admin);
        //查询列表
        List<AdminDto> adminList = adminDao.getAdminList(startNum, size, admin, adminGroup);

        return new PageResultUtil<>(page, size, total, adminList);
    }

    /**
     * 获取管理员详情
     * 用于内部查询，编辑
     * */
    @Transactional
    public Admin getAdminDetail(Long adminId){
        //判断用户ID
        if((adminId == null) || (adminId == 0)){
            throw new ReturnException("管理员ID不能为空！");
        }

        return adminDao.getAdminDetail(adminId);
    }

    /**
     * 获取管理员详情
     * 重新组装展示字段，脱敏处理
     * */
    @Transactional
    public AdminDto getAdminDetailDto(Long adminId){
        //判断用户ID
        if((adminId == null) || (adminId == 0)){
            throw new ReturnException("管理员ID不能为空！");
        }

        return adminDao.getAdminDetailDto(adminId);
    }

    //获取管理员数量
    public Integer getAdminTotal(Admin admin){
        return adminDao.getAdminTotal(admin);
    }
}
