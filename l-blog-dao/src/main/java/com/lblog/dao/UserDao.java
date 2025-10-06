package com.lblog.dao;

import com.lblog.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    Long getUserId(String name);

    Integer addUser(User user);

    Integer editUser(User user);

    List<User> getUserList(@Param("startNum") Integer startNum,
                           @Param("size") Integer size,
                           @Param("user") User user);

    User getUserDetail(@Param("id") Long userId);

    Integer getUserTotal(User user);
}
