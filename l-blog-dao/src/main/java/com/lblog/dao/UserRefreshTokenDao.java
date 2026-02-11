package com.lblog.dao;

import com.lblog.domain.UserRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRefreshTokenDao {
    Integer addUserRefreshToken(UserRefreshToken userRefreshToken);

    Integer editUserRefreshToken(UserRefreshToken userRefreshToken);

    Integer deleteUserRefreshToken(UserRefreshToken userRefreshToken);

    Long getUserRefreshTokenId(UserRefreshToken userRefreshToken);
}
