package com.lblog.dao;

import com.lblog.domain.UserToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenDao {
    public Integer addUserToken(UserToken userToken);

    public Integer deleteUserToken(Long userId);
}
