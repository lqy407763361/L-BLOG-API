package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.service.DateRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DateRangeApi {

    @Autowired
    private DateRangeService dateRangeService;

    //获取最近一周的列表
    @GetMapping("/getLastWeekList")
    public JsonResponseUtil<List<String>> getLastWeekList(){
        List<String> lastWeekList = dateRangeService.getLastWeekList();

        return JsonResponseUtil.success(lastWeekList);
    }
}
