package com.lblog.common.util;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class DateRangeUtil {
    //获取从指定天数前到今天的日期列表
    public static Map<String, Object> getDateRange(int daysBefore){
        //指定天数默认为7
        if(daysBefore == 0){
            daysBefore = 7;
        }

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(daysBefore - 1);

        List<Long> timestampList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<String> weekList = new ArrayList<>();

        IntStream.range(0, daysBefore)
                .mapToObj(i -> startDate.plusDays(i))
                .forEach(date -> {
                    timestampList.add(date.atStartOfDay(ZoneOffset.ofHours(8)).toEpochSecond());
                    dateList.add(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    weekList.add(getChineseWeekName(date.getDayOfWeek()));
                });

        Map<String, Object> result = new HashMap<>();
        result.put("timestampList", timestampList);
        result.put("dateList", dateList);
        result.put("weekList", weekList);
        return result;
    }

    //获取中文名
    private static String getChineseWeekName(java.time.DayOfWeek dayOfWeek){
        String[] chineseWeekName = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        return chineseWeekName[dayOfWeek.getValue() - 1];
    }
}
