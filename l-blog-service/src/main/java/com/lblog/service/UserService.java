package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.JwtTokenUtil;
import com.lblog.common.util.MD5Util;
import com.lblog.common.util.PageResultUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.common.validation.FormValidation;
import com.lblog.dao.UserDao;
import com.lblog.dao.UserLoginRecordDao;
import com.lblog.dao.UserRsaKeyDao;
import com.lblog.dao.UserRefreshTokenDao;
import com.lblog.domain.User;
import com.lblog.domain.UserLoginRecord;
import com.lblog.domain.UserRefreshToken;
import com.lblog.dto.UserNameDto;
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
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;

    @Autowired
    private UserRsaKeyDao userRsaKeyDao;

    @Autowired
    private UserRefreshTokenDao userRefreshTokenDao;

    //登录
    @Transactional
    public Map<String, Object> login(User user, String userIp){
        //判断用户是否存在
        String name = user.getName().trim();
        Long userId = userDao.getUserId(name);
        if((userId == null) || (userId == 0)){
            throw new ReturnException("用户不存在！");
        }

        //判断密码是否正确
        String rawPassword = "";
        String password = user.getPassword().trim();
        try {
            String privateKeyBase64 = userRsaKeyDao.getUserRsaKeyByUserId(userId).getPrivateKeyBase64();
            rawPassword = RSAUtil.decrypt(password, privateKeyBase64);
        } catch (Exception e) {
            throw new ReturnException("密码解密失败！");
        }
        String salt = user.getSalt();
        if(!MD5Util.getEncrypt(rawPassword, salt).equals(this.getUserDetail(userId).getPassword())){
            throw new ReturnException("密码错误！");
        }

        //判断用户账号状态
        Integer status = user.getStatus();
        if(status == 2){
            throw new ReturnException("用户已被禁用！");
        }

        //插入登录记录表
        Long addTime = Instant.now().toEpochMilli();
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userId);
        userLoginRecord.setLoginIp(userIp);
        userLoginRecord.setLoginTime(addTime);
        userLoginRecordDao.addUserLoginRecord(userLoginRecord);

        //保存refreshToken，返回accessToken/refreshToken/rsaKeyId
        Long rsaKeyId = userRsaKeyDao.getUserRsaKeyByUserId(userId).getId();
        String privateKeyBase64 = userRsaKeyDao.getUserRsaKeyByUserId(userId).getPrivateKeyBase64();
        PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyBase64);
        String accessToken = JwtTokenUtil.generateAccessToken(userId, privateKey);
        String refreshToken = JwtTokenUtil.generateRefreshToken(userId, privateKey);
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUserId(userId);
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setIsRevoked(0);
        userRefreshToken.setAddTime(addTime);
        userRefreshTokenDao.addUserRefreshToken(userRefreshToken);

        Map<String, Object> result = new HashMap<>();
        result.put("rsaKeyId", rsaKeyId);
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    //注册
    @Transactional
    public Map<String, Object> register(User user, String userIp){
        //判断账号格式是否合法和是否已存在
        String name = user.getName().trim();
        if(!FormValidation.userNameValidation(name)){
            throw new ReturnException("账号格式错误！");
        }
        Long existUserId = userDao.getUserId(name);
        if((existUserId != null) && (existUserId > 0)){
            throw new ReturnException("用户已存在！");
        }

        //判断密码格式是否合法
        String password = user.getPassword().trim();
        if(!FormValidation.passwordValidation(password)){
            throw new ReturnException("密码格式错误！");
        }

        //添加操作
        Integer registerType = user.getRegisterType();
        Long addTime = Instant.now().toEpochMilli();
        String salt = addTime + MD5Util.RandomString(8);
        password = MD5Util.getEncrypt(password, salt);
        user.setName(name);
        user.setPassword(password);
        user.setSalt(salt);
        user.setStatus(1);
        user.setRegisterType(registerType);
        user.setRegisterIp(userIp);
        user.setAddTime(addTime);
        Integer returnRow = userDao.addUser(user);
        if((returnRow == null) || (returnRow == 0)){
            throw new ReturnException("注册失败！");
        }

        //插入登录记录表
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        Long userId = user.getId();
        userLoginRecord.setUserId(userId);
        userLoginRecord.setLoginIp(userIp);
        userLoginRecord.setLoginTime(addTime);
        userLoginRecordDao.addUserLoginRecord(userLoginRecord);

        //保存refreshToken，返回accessToken/refreshToken/rsaKeyId
        Long rsaKeyId = userRsaKeyDao.getUserRsaKeyByUserId(userId).getId();
        String privateKeyBase64 = userRsaKeyDao.getUserRsaKeyByUserId(userId).getPrivateKeyBase64();
        PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyBase64);
        String accessToken = JwtTokenUtil.generateAccessToken(userId, privateKey);
        String refreshToken = JwtTokenUtil.generateRefreshToken(userId, privateKey);
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUserId(userId);
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setIsRevoked(0);
        userRefreshToken.setAddTime(addTime);
        userRefreshTokenDao.addUserRefreshToken(userRefreshToken);

        Map<String, Object> result = new HashMap<>();
        result.put("rsaKeyId", rsaKeyId);
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    //退出登录
    @Transactional
    public void loginOut(Long userId, String refreshToken){
        //判断用户ID
        if((userId == null) || (userId == 0)){
            throw new ReturnException("用户ID不能为空！");
        }

        //判断refreshToken
        if(StringUtils.isBlank(refreshToken)){
            throw new ReturnException("refreshToken不能为空！");
        }

        //判断refreshToken
        Long editTime = Instant.now().toEpochMilli();
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUserId(userId);
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setEditTime(editTime);
        Long refreshTokenId = userRefreshTokenDao.getUserRefreshTokenId(userRefreshToken);
        if((refreshTokenId == null) || (refreshTokenId == 0L)){
            throw new ReturnException("refreshToken不存在！");
        }

        userRefreshTokenDao.deleteUserRefreshToken(userRefreshToken);
    }

    //刷新token
    @Transactional
    public String refreshAccessToken(Long userId, String refreshToken){
        //判断用户ID
        if((userId == null) || (userId == 0)){
            throw new ReturnException("用户ID不能为空！");
        }

        //判断refreshToken
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUserId(userId);
        userRefreshToken.setRefreshToken(refreshToken);
        Long refreshTokenId = userRefreshTokenDao.getUserRefreshTokenId(userRefreshToken);
        if((refreshTokenId == null) || (refreshTokenId == 0L)){
            throw new ReturnException("refreshToken不存在！");
        }

        //验证refreshToken合法性
        String publicKeyBase64 = this.getRsaPublicKeyByUserId(userId);
        PublicKey publicKey = RSAUtil.getPublicKey(publicKeyBase64);
        JwtTokenUtil.validateToken(refreshToken, publicKey);
        String privateKeyBase64 = userRsaKeyDao.getUserRsaKeyByUserId(userId).getPrivateKeyBase64();
        PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyBase64);
        String accessToken = JwtTokenUtil.generateAccessToken(userId, privateKey);

        return accessToken;
    }

    //编辑用户
    @Transactional
    public void editUser(User user){
        //判断用户是否存在
        Long userId = user.getId();
        User userDetail = userDao.getUserDetail(userId);
        if(userDetail == null){
            throw new ReturnException("用户不存在！");
        }

        //更改用户账号状态
        Integer status = user.getStatus();
        Long editTime = Instant.now().toEpochMilli();
        user.setStatus(status);
        user.setEditTime(editTime);
        userDao.editUser(user);
    }

    //根据userId查询用户公钥
    public String getRsaPublicKeyByUserId(Long userId){
        String publicKeyBase64 = userRsaKeyDao.getUserRsaKeyByUserId(userId).getPublicKeyBase64();

        return publicKeyBase64;
    }

    //根据rsaKeyId查询用户公钥
    public String getRsaPublicKeyById(Long id){
        String publicKeyBase64 = userRsaKeyDao.getUserRsaKeyById(id).getPublicKeyBase64();

        return publicKeyBase64;
    }

    //获取用户列表
    public PageResultUtil<User> getUserList(Integer startPage, Integer size, User user){
        //起始位置
        Integer startNum = (startPage-1) * size;
        //获取总数
        Integer total = userDao.getUserTotal(user);
        //查询列表
        List<User> userList = userDao.getUserList(startNum, size, user);

        return new PageResultUtil<>(startPage, size, total, userList);
    }

    //获取用户详情
    @Transactional
    public User getUserDetail(Long userId){
        //判断用户ID
        if((userId == null) || (userId == 0)){
            throw new ReturnException("用户ID不能为空！");
        }

        return userDao.getUserDetail(userId);
    }

    //获取用户数量
    public Integer getUserTotal(User user){
        return userDao.getUserTotal(user);
    }

    //获取用户名称
    @Transactional
    public UserNameDto getUserName(Long userId){
        //判断用户ID
        if((userId == null) || (userId == 0)){
            throw new ReturnException("用户ID不能为空！");
        }

        User user = userDao.getUserDetail(userId);
        return UserNameDto.getUserName(user);
    }
}
