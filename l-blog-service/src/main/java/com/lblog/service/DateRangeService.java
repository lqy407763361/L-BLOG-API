package com.lblog.service;

import com.lblog.common.util.DateRangeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateRangeService {
    //获取最近一周的列表
    public List<String> getLastWeekList(){
        return DateRangeUtil.getDateRange(7);
    }
}
