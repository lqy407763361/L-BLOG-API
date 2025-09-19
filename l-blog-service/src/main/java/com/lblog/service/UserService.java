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
import java.util.Map;

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
    @Transactional
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
            String privateKeyBase64 = userRsaKeyDao.getUserRsaKey(userId).getPrivateKeyBase64();
            PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyBase64);
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
            String privateKeyBase64 = userRsaKeyDao.getUserRsaKey(userId).getPrivateKeyBase64();
            PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyBase64);
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
    @Transactional
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

    //测试
    public PrivateKey test(){
        String privateKeyBase64 = "\"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALwnvQDK2/qOI1zMkggtUFydGbGmH+Ck8mdh9kavr3PLWxE3OlmAKmrNFvQ35+C0qTCIyQuQJoJgcbEYtX+auR+pnVUsLwwQy8aNWREr4uTf1w3/oBkFCRrTNwz84hEEx56t3FePQfmLlNzf+DKsvoEHGVSVrjK6QZTlofUBQnkDAgMBAAECgYAT8pKTFu6jbZZKLFX/D+7JIs6qitYuVs5sL3KQo+eR+yk4dgZ1nqTglcWtNpAavoyBXL8TvsCWaesjv17enGSry/c370cqqqiqmwSUHFmJizCR9C7FgMMcaE6h8/mfWytNhkfMxFBL4EP7hTfQ2dWfOOGJJPymdE7rYJnCimf/QQJBAO78JyjE2KVrrFuJr1qWcBFwQHt37yMZllEqJicayu14CqnUziRQ0lQG6lkSNCPrcKeQUX74GMXLuThua8I8bgkCQQDJjSPAiYVxcUycP7fahd1rbmyo0/J8ErLOk4Y0aZB9M0Fsnu6EQJWVlqaU7IzX4G/IlvBHR+GeyBuoS7z4e3GrAkBGD7w3MoZE8K0F5PZ4ezP9mMf+qml8A8tSniWzPyKQval6on2QnfUbVy+qzzBj+2j6Zs/NhlRU4GW7inui5O5pAkEAs+tJWgupUr5oPCbpMapEIS3e08r38GgktCGfMNR3hjwmEBfEJc0dev6Tz+dmRyNzxiVvcsIpFzvc7JxHoa1YpwJAb2TqGot3zheaonhPPTAVeK2YS6+GGmo8CoKPyLgRYJszj7GLyIww7Nwc91IQZ2bLwzjhyYaG41HjeJUWH80vkw==\"";
        PrivateKey testRes = RSAUtil.getPrivateKey(privateKeyBase64);

        return testRes;
    }
}
