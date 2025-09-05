package com.lblog.dao;

import com.lblog.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public Long getUserId(String name);

    public User getUserOne(String name);

    public Integer addUser(User user);
}
