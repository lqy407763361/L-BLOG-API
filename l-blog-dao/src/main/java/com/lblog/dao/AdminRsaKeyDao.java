package com.lblog.dao;

import com.lblog.domain.AdminRsaKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminRsaKeyDao {
    AdminRsaKey getAdminRsaKeyById(Long id);

    AdminRsaKey getAdminRsaKeyByAdminId(Long adminId);

    Integer addAdminRsaKey(AdminRsaKey adminRsaKey);
}
