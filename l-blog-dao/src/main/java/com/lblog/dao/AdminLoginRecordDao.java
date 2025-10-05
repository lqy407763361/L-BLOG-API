package com.lblog.dao;

import com.lblog.domain.AdminLoginRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminLoginRecordDao {
    Integer addAdminLoginRecord(AdminLoginRecord adminLoginRecord);
}
