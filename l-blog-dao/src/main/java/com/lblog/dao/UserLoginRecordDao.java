package com.lblog.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginRecordDao {
    public void addUserLoginRecord(Integer userId);
}
