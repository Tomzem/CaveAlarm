package com.tomzem.cavealarm.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Tomze
 * @time 2019年03月02日 23:15
 * @desc 时间管理类
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    public static final String FORMAT_HM = "HH:mm";
    public static final String FORMAT_HH = "HH";
    public static final String FORMAT_MM = "mm";

    /**
     * 获取时间戳
     * @return 获取时间戳
     */
    public static Long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     *  获取当前时间 00：00
     * @return
     */
    public static String getCurrentHourMin() {
        return parse(FORMAT_HM);
    }

    /**
     * 获取时分数组
     * @param date
     * @return
     */
    public static int[] getHourMinByDate(Date date) {
        return new int[]{Integer.parseInt(parse(date, FORMAT_HH)),
                Integer.parseInt(parse(date, FORMAT_MM))};
    }

    /**
     * 根据date格式获取calendar格式
     * @param date
     * @return
     */
    public static Calendar getCalendarByDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar selectedDate = Calendar.getInstance();
        int[] select = TimeUtils.getHourMinByDate(date);
        selectedDate.set(0,0,0, select[0], select[1]);
        return selectedDate;
    }

    /**
     * 格式化时间
     * @param format
     * @return
     */
    public static String parse(String format) {
        return parse(null, format);
    }

    /**
     *  格式化时间
     * @param date
     * @param format
     * @return
     */
    public static String parse(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        if (date == null) {
            return df.format(new Date());
        } else {
            return df.format(date);
        }
    }
}
