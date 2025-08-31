package com.lblog.service;

import com.lblog.dao.UserDao;
import com.lblog.domain.User;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void login(User user){
        String name = user.getName();
        String password = user.getPassword();
        Long add_time = System.currentTimeMillis();

        if(StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(password)){
            User userOne = this.getUserOne(name);
            if(userOne == null){

            }
        }
    }

    public User getUserOne(String name){
        return userDao.getUserOne(name);
    }
}
