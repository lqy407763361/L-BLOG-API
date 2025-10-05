package com.lblog.dao;

import com.lblog.domain.AdminRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminRefreshTokenDao {
    Integer addAdminRefreshToken(AdminRefreshToken adminRefreshToken);

    Integer deleteAdminRefreshToken(AdminRefreshToken adminRefreshToken);

    Long getAdminRefreshTokenId(AdminRefreshToken adminRefreshToken);
}
