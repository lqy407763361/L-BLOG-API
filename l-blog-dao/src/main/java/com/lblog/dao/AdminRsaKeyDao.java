package com.lblog.dao;

import com.lblog.domain.AdminRsaKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminRsaKeyDao {
    AdminRsaKey getAdminRsaKeyById(Long id);

    AdminRsaKey getAdminRsaKeyByAdminId(Long adminId);

    Integer addAdminRsaKey(AdminRsaKey adminRsaKey);

    Integer deleteAdminRsaKey(@Param("adminId") List<Long> adminId);
}
