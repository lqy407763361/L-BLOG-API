package com.lblog.dao;

import com.lblog.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginRecordDao {
    public Integer addUserLoginRecord(User user);
}
