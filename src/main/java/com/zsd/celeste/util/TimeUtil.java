package com.zsd.celeste.util;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

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


    public static LocalDateTime getRandomDateTime(LocalDateTime start, LocalDateTime end) {
        if (start == null)
            start = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        if (end == null)
            end = LocalDateTime.now();

        // 获取开始和结束时间的时间戳
        long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(ZoneOffset.UTC);

        // 生成随机时间戳
        Random random = new Random();
        long randomEpoch = startEpoch + (long) (random.nextDouble() * (endEpoch - startEpoch));

        // 将随机时间戳转换回LocalDateTime
        return LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneOffset.UTC);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        // 定义格式化方式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化LocalDateTime并返回字符串
        return dateTime.format(formatter);
    }
}
