package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.service.DateRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DateRangeApi {

    @Autowired
    private DateRangeService dateRangeService;

    //获取最近一周的列表
    @GetMapping("/getLastWeekList")
    public JsonResponseUtil<Map<String, Object>> getLastWeekList(){
        Map<String, Object> lastWeekList = dateRangeService.getLastWeekList();

        return JsonResponseUtil.success(lastWeekList);
    }

    //获取最近一周的访问人次列表
    @GetMapping("/getLastWeekVisitsList")
    public JsonResponseUtil<Map<String, Object>> getLastWeekVisitsList(){
        Map<String, Object> lastWeekVisitsList = dateRangeService.getLastWeekVisitsList();

        return JsonResponseUtil.success(lastWeekVisitsList);
    }
}
