package com.zsd.celeste.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

@Component
public class TimeUtil {

    static public Date getMonday(){
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 获取星期一
        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // LocalDate 转 LocalDateTime
        LocalDateTime weekStart = monday.atStartOfDay();
        // 转Date
        return Date.from(weekStart.atZone(ZoneId.systemDefault()).toInstant());
    }
}
