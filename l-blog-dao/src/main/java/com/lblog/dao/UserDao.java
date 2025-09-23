package com.lblog.dao;

import com.lblog.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public Long getUserId(String name);

    public Integer addUser(User user);

    public Integer editUser(User user);

    public User getUserList();

    public User getUserDetail(Long userId);
}
