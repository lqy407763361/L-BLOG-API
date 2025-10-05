package com.lblog.dao;

import com.lblog.domain.Admin;
import com.lblog.domain.AdminGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminDao {
    Long getAdminId(String account);

    Integer addAdmin(Admin admin);

    Integer editAdmin(Admin admin);

    Integer deleteAdmin(Long adminId);

    List<Admin> getAdminList(@Param("startNum") Integer startNum,
                             @Param("size") Integer size,
                             @Param("admin") Admin admin,
                             @Param("adminGroup")AdminGroup adminGroup);

    Admin getAdminDetail(Long adminId);

    Integer getAdminTotal(Admin admin);
}
