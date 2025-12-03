package com.lblog.dao;

import com.lblog.domain.Admin;
import com.lblog.domain.AdminGroup;
import com.lblog.dto.AdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminDao {
    Long getAdminId(String account);

    Integer addAdmin(Admin admin);

    Integer editAdmin(Admin admin);

    Integer deleteAdmin(@Param("id") List<Long> adminId);

    List<AdminDto> getAdminList(@Param("startNum") Integer startNum,
                                @Param("size") Integer size,
                                @Param("admin") Admin admin,
                                @Param("adminGroup") AdminGroup adminGroup);

    Admin getAdminDetail(@Param("id") Long adminId);

    AdminDto getAdminDetailDto(@Param("id") Long adminId);

    Integer getAdminTotal(Admin admin);

    Integer getAdminTotalByGroupId(@Param("groupId") List<Long> adminGroupId);
}
