package com.lblog.dao;

import com.lblog.domain.UserRsaKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRsaKeyDao {
    UserRsaKey getUserRsaKeyById(Long id);

    UserRsaKey getUserRsaKeyByUserId(Long userId);

    Integer addUserRsaKey(UserRsaKey userRsaKey);
}
