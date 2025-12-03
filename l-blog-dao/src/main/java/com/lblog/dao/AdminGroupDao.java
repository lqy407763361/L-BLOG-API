package com.lblog.dao;

import com.lblog.domain.AdminGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminGroupDao {
    Long getAdminGroupId(String name);

    Integer addAdminGroup(AdminGroup adminGroup);

    Integer editAdminGroup(AdminGroup adminGroup);

    Integer deleteAdminGroup(@Param("id") List<Long> adminGroupId);

    List<AdminGroup> getAdminGroupList(@Param("startNum") Integer startNum,
                                       @Param("size") Integer size,
                                       @Param("adminGroup") AdminGroup adminGroup);

    AdminGroup getAdminGroupDetail(@Param("id") Long adminGroupId);

    Integer getAdminGroupTotal(AdminGroup adminGroup);
}
