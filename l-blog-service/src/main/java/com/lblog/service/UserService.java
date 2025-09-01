package com.lblog.service;

import com.lblog.dao.UserDao;
import com.lblog.dao.UserLoginRecordDao;
import com.lblog.domain.User;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;

    //登录
    public void login(User user){
        String name = user.getName();
        String password = user.getPassword();
        Long add_time = System.currentTimeMillis();

        //判断账号是否符合条件
        if(StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(password)){
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

        //判断账号是否符合条件
        if(StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(password)){
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
    public void loginOut(Integer userId){
    }

    //获取用户ID
    public Integer getUserId(String name){
        return userDao.getUserId(name);
    }
}
