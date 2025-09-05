package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.validation.FormValidation;
import com.lblog.dao.UserDao;
import com.lblog.dao.UserLoginRecordDao;
import com.lblog.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String name = user.getName().trim();
        String password = user.getPassword().trim();
        Long add_time = System.currentTimeMillis();
        String user_ip = GetClientIpUtil.GetClientIp(request);

        //判断用户是否存在
        Long userId = this.getUserId(name);
        if(userId == null){
            throw new ReturnException("用户不存在！");
        }

        //判断密码是否正确
        if(!password.equals(this.getUserOne(name).getPassword())){
            throw new ReturnException("密码错误！");
        }

        //登录操作 并且插入登录记录表
        try{
            userLoginRecordDao.addUserLoginRecord(user);
        }catch(Exception e){
            throw new ReturnException("登录失败！");
        }
    }

    //注册
    @Transactional
    public void register(User user){
        String name = user.getName();
        String password = user.getPassword();
        Long addTime = System.currentTimeMillis();
        String userIp = GetClientIpUtil.GetClientIp(request);

        //判断账号是否符合条件
        if(FormValidation.userNameValidation(name) && FormValidation.passwordValidation(password)){
            throw new ReturnException("账号或密码格式错误！");
        }

        //添加操作 并且插入登录记录表
        try{
            Integer returnRow = userDao.addUser(user);
            if(returnRow<1){
                throw new ReturnException("注册失败！");
            }
            Long userId = user.getId();
            userLoginRecordDao.addUserLoginRecord(user);
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
    private User getUserOne(String name){
        return userDao.getUserOne(name);
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
