package com.lblog.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public Integer getUserId(String name);

    public Integer addUser(String name);
}
