package com.lblog.service;

import com.lblog.common.util.DateRangeUtil;
import com.lblog.dao.UserVisitRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DateRangeService {

    @Autowired
    private UserVisitRecordDao userVisitRecordDao;

    //获取最近一周的时间列表
    public Map<String, Object> getLastWeekList(){
        return DateRangeUtil.getDateRange(7);
    }

    //获取最近一周的访问人次列表
    public Map<String, Object> getLastWeekVisitsList(){
        //最近一周的时间列表
        Map<String, Object> lastWeekList = this.getLastWeekList();

        //按天计算的访问人次
        List<Long> timestampList = (List<Long>) lastWeekList.get("timestampList");
        Long startTime = timestampList.get(0);
        Long endTime = timestampList.get(timestampList.size() - 1);
        List<Map<String, Object>> dailyUserVisits = userVisitRecordDao.getDailyUserVisits(startTime, endTime);

        //转换及合并结果
        Map<String, Object> visitsMap = dailyUserVisits.stream()
                .collect(Collectors.toMap(
                        item -> item.get("visit_time").toString(),
                        item -> item.get("visits")
                ));
        List<String> dateList = (List<String>) lastWeekList.get("dateList");
        List<Object> visitsList = new ArrayList<>();
        for(String date:dateList){
            visitsList.add(visitsMap.getOrDefault(date, 0));
        }
        lastWeekList.put("visitList", visitsList);

        return lastWeekList;
    }
}
