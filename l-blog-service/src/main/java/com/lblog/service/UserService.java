package com.lblog.service;

import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.validation.FormValidation;
import com.lblog.dao.UserDao;
import com.lblog.dao.UserLoginRecordDao;
import com.lblog.domain.User;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;

    @Autowired
    private HttpServletRequest request;

    //登录
    public void login(User user){
        String name = user.getName();
        String password = user.getPassword();
        Long add_time = System.currentTimeMillis();
        String user_ip = GetClientIpUtil.GetClientIp(request);

        //判断账号是否符合条件
        if(FormValidation.userNameValidation(name) || FormValidation.passwordValidation(password)){
        }
        Integer userId = this.getUserId(name);
        if(userId == null){
        }

        //登录操作 并且插入登录记录表
        userLoginRecordDao.addUserLoginRecord(userId);
    }

    //注册
    public void register(User user){
        String name = user.getName();
        String password = user.getPassword();
        Long add_time = System.currentTimeMillis();
        String user_ip = GetClientIpUtil.GetClientIp(request);

        //判断账号是否符合条件
        if(FormValidation.userNameValidation(name) || StringUtils.isNullOrEmpty(password)){
        }
        Integer userId = this.getUserId(name);
        if(userId != null){
        }

        //添加操作 并且插入登录记录表
        userId = userDao.addUser(name);
        try{
            userLoginRecordDao.addUserLoginRecord(userId);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    //退出登录
    public Integer loginOut(Integer userId){
        return 1;
    }

    //获取用户ID
    private Integer getUserId(String name){
        return userDao.getUserId(name);
    }

    public boolean test(String phone){
        return FormValidation.phoneValidation(phone);
    }
}
