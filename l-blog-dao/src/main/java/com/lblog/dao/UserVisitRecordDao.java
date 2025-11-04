package com.lblog.dao;

import com.lblog.domain.UserVisitRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVisitRecordDao {
    Integer addUserVisitRecord(UserVisitRecord userVisitRecord);
}
