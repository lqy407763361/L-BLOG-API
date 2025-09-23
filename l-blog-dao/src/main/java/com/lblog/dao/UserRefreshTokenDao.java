package com.lblog.dao;

import com.lblog.domain.UserRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRefreshTokenDao {
    public Integer addUserRefreshToken(UserRefreshToken userRefreshToken);

    public Integer deleteUserRefreshToken(UserRefreshToken userRefreshToken);

    public Long getUserRefreshTokenId(UserRefreshToken userRefreshToken);
}
