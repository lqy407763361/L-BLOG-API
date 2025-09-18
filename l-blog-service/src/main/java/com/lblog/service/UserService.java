package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.util.JwtTokenUtil;
import com.lblog.common.util.MD5Util;
import com.lblog.common.util.RSAUtil;
import com.lblog.common.validation.FormValidation;
import com.lblog.dao.UserDao;
import com.lblog.dao.UserLoginRecordDao;
import com.lblog.dao.UserRsaKeyDao;
import com.lblog.dao.UserTokenDao;
import com.lblog.domain.User;
import com.lblog.domain.UserLoginRecord;
import com.lblog.domain.UserToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;

    @Autowired
    private UserRsaKeyDao userRsaKeyDao;

    @Autowired
    private UserTokenDao userTokenDao;

    //登录
    public String login(User user){
        //判断用户是否存在
        Long userId = user.getId();
        if((userId == null) || (userId == 0)){
            throw new ReturnException("用户不存在！");
        }

        //判断密码是否正确
        String rawPassword = "";
        String password = user.getPassword().trim();
        try {
            String privateKeyBase64 = userRsaKeyDao.getUserRsaKey(userId).getPrivateKeyBase64();
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
        String userIp = GetClientIpUtil.getClientIp(request);
        Long addTime = Instant.now().toEpochMilli();
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userId);
        userLoginRecord.setLoginIp(userIp);
        userLoginRecord.setLoginTime(addTime);
        userLoginRecordDao.addUserLoginRecord(userLoginRecord);

        try{
            //保存token并返回
            PrivateKey privateKey = userRsaKeyDao.getUserRsaKey(userId).getPrivateKey();
            String token = JwtTokenUtil.generateToken(userId, privateKey);
            UserToken userToken = new UserToken();
            userToken.setId(userId);
            userToken.setToken(token);
            userTokenDao.addUserToken(userToken);

            return token;
        }catch(Exception e){
            throw new ReturnException("登录失败！");
        }
    }

    //注册
    @Transactional
    public String register(User user){
        //判断用户是否存在
        String name = user.getName().trim();
        Long existUserId = userDao.getUserId(name);
        if((existUserId != null) && (existUserId > 0)){
            throw new ReturnException("用户已存在！");
        }

        //判断账号是否符合条件
        String password = user.getPassword().trim();
        if(!FormValidation.userNameValidation(name) && !FormValidation.passwordValidation(password)){
            throw new ReturnException("账号或密码格式错误！");
        }

        //添加操作
        Integer registerType = user.getRegisterType();
        String userIp = GetClientIpUtil.getClientIp(request);
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
        if((returnRow==null) || (returnRow.equals(0))){
            throw new ReturnException("注册失败！");
        }

        //插入登录记录表
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        Long userId = user.getId();
        userLoginRecord.setUserId(userId);
        userLoginRecord.setLoginIp(userIp);
        userLoginRecord.setLoginTime(addTime);
        userLoginRecordDao.addUserLoginRecord(userLoginRecord);

        try{
            //保存token并返回
            PrivateKey privateKey = userRsaKeyDao.getUserRsaKey(userId).getPrivateKey();
            String token = JwtTokenUtil.generateToken(userId, privateKey);
            UserToken userToken = new UserToken();
            userToken.setId(userId);
            userToken.setToken(token);
            userTokenDao.addUserToken(userToken);

            return token;
        }catch(Exception e){
            throw new ReturnException("注册失败！");
        }
    }

    //退出登录
    public void loginOut(Long userId){
        userTokenDao.deleteUserToken(userId);
    }

    //编辑用户
    public void editUser(User user){
        //判断用户是否存在
        Long userId = user.getId();
        User userInfo = userDao.getUserDetail(userId);
        if(userInfo == null){
            throw new ReturnException("用户不存在！");
        }

        //更改用户账号状态
        Integer status = user.getStatus();
        Long editTime = Instant.now().toEpochMilli();
        user.setStatus(status);
        user.setEditTime(editTime);
        userDao.editUser(user);
    }

    //查询该用户公钥
    public String getUserRsaPublicKey(Long userId){
        return userRsaKeyDao.getUserRsaKey(userId).getPublicKeyBase64();
    }

    //获取用户详情
    public User getUserDetail(Long userId){
        return userDao.getUserDetail(userId);
    }

    //获取用户列表
    public User getUserList(){
        return userDao.getUserList();
    }
}
