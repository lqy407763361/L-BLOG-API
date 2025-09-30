package com.lblog.dao;

import com.lblog.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    public Long getUserId(String name);

    public Integer addUser(User user);

    public Integer editUser(User user);

    public List<User> getUserList(@Param("startNum") Integer startNum,
                                  @Param("size") Integer size);

    public User getUserDetail(Long userId);

    public Integer getUserTotal();
}
