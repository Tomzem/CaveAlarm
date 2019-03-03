package com.tomzem.cavealarm.utils;

import android.annotation.SuppressLint;

import com.search.baselibrary.utils.DateUtil;

import java.text.ParseException;
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

    public static final String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YM = "yyyy-MM";
    public static final String FORMAT_YMD = "yyyy-MM-dd";

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

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMD_HM);
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMD_HMS);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取年月
     * @param date
     * @return
     */
    public static String getYearMonth(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YM);
        return simpleDateFormat.format(date);
    }

    /**
     *  获取明天
     * @return
     */
    public static String getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YMD);
        return formatter.format(calendar.getTime());
    }

    /**
     * 计算两个时间差
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(long endDate, long nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate - nowDate;
        // 计算差多少天
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        if (day != 0) {
            return day + "天" + hour + "小时" + min + "分钟";
        } else if (hour != 0){
            return hour + "小时" + min + "分钟";
        } else {
            return min + "分钟";
        }
    }
}
