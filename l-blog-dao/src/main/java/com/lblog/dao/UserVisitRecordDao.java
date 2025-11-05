package com.lblog.dao;

import com.lblog.domain.UserVisitRecord;
import com.lblog.dto.UserVisitRecordDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserVisitRecordDao {
    Integer addUserVisitRecord(UserVisitRecord userVisitRecord);

    List<UserVisitRecordDto> getUserVisitRecordList(@Param("startNum") Integer startNum,
                                                    @Param("size") Integer size,
                                                    @Param("startTime") Long startTime,
                                                    @Param("endTime") Long endTime);

    Integer getUserVisitRecordTotal(@Param("startTime") Long startTime,
                                    @Param("endTime") Long endTime);

    List<Map<String, Object>> getDailyUserVisits(@Param("startTime") Long startTime,
                                                 @Param("endTime") Long endTime);
}
