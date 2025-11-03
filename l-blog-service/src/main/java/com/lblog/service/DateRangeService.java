package com.lblog.service;

import com.lblog.common.util.DateRangeUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DateRangeService {
    //获取最近一周的列表
    public Map<String, Object> getLastWeekList(){
        return DateRangeUtil.getDateRange(7);
    }
}
