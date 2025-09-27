package com.lblog.dao;

import com.lblog.domain.UserRsaKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRsaKeyDao {
    public UserRsaKey getUserRsaKeyByUserId(Long userId);

    public UserRsaKey getUserRsaKeyById(Long id);
}
