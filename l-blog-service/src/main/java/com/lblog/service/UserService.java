package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.util.MD5Util;
import com.lblog.common.util.RSAUtil;
import com.lblog.common.validation.FormValidation;
import com.lblog.dao.UserDao;
import com.lblog.dao.UserLoginRecordDao;
import com.lblog.dao.UserRsaKeyDao;
import com.lblog.domain.User;
import com.lblog.domain.UserLoginRecord;
import com.lblog.domain.UserRsaKey;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;

    @Autowired
    private UserRsaKeyDao userRsaKeyDao;

    @Autowired
    private HttpServletRequest request;

    //登录
    public void login(User user){
        String name = user.getName().trim();
        String basePassword = user.getPassword().trim();
        String userIp = GetClientIpUtil.getClientIp(request);
        Long addTime = Instant.now().toEpochMilli();

        //判断用户是否存在
        Long userId = this.getUserId(name);
        if(userId == null){
            throw new ReturnException("用户不存在！");
        }

        //判断密码是否正确
        String password;
        try {
            String privateKeyBase64 = userRsaKeyDao.getUserRsaKey(userId).getPrivateKey();
            password = RSAUtil.decrypt(basePassword, privateKeyBase64);
        } catch (Exception e) {
            throw new ReturnException("密码解密失败！");
        }
        String salt = this.getUserOne(userId).getSalt();
        if(!MD5Util.getEncrypt(password, salt).equals(this.getUserOne(userId).getPassword())){
            throw new ReturnException("密码错误！");
        }

        //登录操作 并且插入登录记录表
        try{
            UserLoginRecord userLoginRecord = new UserLoginRecord();
            userLoginRecord.setUserId(userId);
            userLoginRecord.setLoginIp(userIp);
            userLoginRecord.setLoginTime(addTime);
            userLoginRecordDao.addUserLoginRecord(userLoginRecord);
        }catch(Exception e){
            throw new ReturnException("登录失败！");
        }
    }

    //注册
    @Transactional
    public void register(User user){
        String name = user.getName().trim();
        String password = user.getPassword().trim();
        Long addTime = Instant.now().toEpochMilli();
        String userIp = GetClientIpUtil.getClientIp(request);

        //判断账号是否符合条件
        if(!FormValidation.userNameValidation(name) && !FormValidation.passwordValidation(password)){
            throw new ReturnException("账号或密码格式错误！");
        }

        //添加操作 并且插入登录记录表
        try{
            String salt = "";
            password = MD5Util.getEncrypt(password, salt);
            user.setName(name);
            user.setPassword(password);
            user.setAddTime(addTime);
            Integer returnRow = userDao.addUser(user);
            if((returnRow==null) || (returnRow<1)){
                throw new ReturnException("注册失败！");
            }

            Long userId = user.getId();
            UserLoginRecord userLoginRecord = new UserLoginRecord();
            userLoginRecord.setUserId(userId);
            userLoginRecord.setLoginIp(userIp);
            userLoginRecord.setLoginTime(addTime);
            userLoginRecordDao.addUserLoginRecord(userLoginRecord);
        }catch(Exception e){
            throw new ReturnException("注册失败！");
        }
    }

    //退出登录
    public Integer loginOut(Long userId){
        return 1;
    }

    //获取用户ID
    private Long getUserId(String name){
        Long userId = userDao.getUserId(name);
        if(userId == null){
            throw new ReturnException("用户不存在！");
        }

        return userId;
    }

    //获取用户数据
    private User getUserOne(Long userId){
        return userDao.getUserOne(userId);
    }

    //临时测试
    public Long test(String test){
        Long userId =  userDao.getUserId(test);
        if(userId == null){
            throw new ReturnException("用户不存在！");
        }

        return userId;
    }
}
