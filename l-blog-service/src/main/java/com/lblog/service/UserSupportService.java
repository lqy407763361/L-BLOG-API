package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.dao.UserDao;
import com.lblog.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserSupportService {
    private UserDao userDao;

    //获取用户ID
    public Long getUserId(String name){
        Long userId = userDao.getUserId(name);
        if(userId == null){
            throw new ReturnException("用户不存在！");
        }

        return userId;
    }

    //获取单个用户数据
    public User getUserDetail(String name){
        Long userId = userDao.getUserId(name);
        if(userId == null){
            throw new ReturnException("用户不存在！");
        }
        
        return userDao.getUserDetail(userId);
    }
}
