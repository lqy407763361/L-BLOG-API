package com.lblog.common.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateRangeUtil {
    //获取从指定天数前到今天的日期列表
    public static List<String> getDateRange(int daysBefore){
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(daysBefore - 1);

        return IntStream.range(0, daysBefore)
                .mapToObj(i -> startDate.plusDays(i))
                .map(date -> {
                    String dayOfWeek = getChineseWeekName(date.getDayOfWeek());
                    return date.format(DateTimeFormatter.ofPattern("MM-dd")) + " " + dayOfWeek;
                })
                .collect(Collectors.toList());
    }

    //获取中文名
    private static String getChineseWeekName(java.time.DayOfWeek dayOfWeek){
        String[] chineseWeekName = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        return chineseWeekName[dayOfWeek.getValue() - 1];
    }
}
