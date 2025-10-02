package com.lblog.dao;

import com.lblog.domain.UserLoginRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginRecordDao {
    Integer addUserLoginRecord(UserLoginRecord userLoginRecord);
}
