package com.tomzem.cavealarm.utils;

import android.annotation.SuppressLint;

import com.search.baselibrary.utils.UiUtils;
import com.tomzem.cavealarm.R;

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
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    public static final String FORMAT_YM = "yyyy-MM";
    public static final String FORMAT_YY = "yyyy";

    public static final String FORMAT_HM = "HH:mm";
    public static final String FORMAT_HH = "HH";
    public static final String FORMAT_MM = "mm";
    public static final String FORMAT_EE = "EEEE";

    public static final String[] WEEK = UiUtils.getResources().getStringArray(R.array.week_array);

    /**
     * 获取时间戳
     *
     * @return 获取时间戳
     */
    public static Long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 获取当前时间 00：00
     *
     * @return
     */
    public static String getCurrentHourMin() {
        return parse(FORMAT_HM);
    }

    /**
     * 获取时分数组
     *
     * @param date
     * @return
     */
    public static int[] getHourMinByDate(Date date) {
        return new int[]{Integer.parseInt(parse(date, FORMAT_HH)),
                Integer.parseInt(parse(date, FORMAT_MM))};
    }

    /**
     * 根据date格式获取calendar格式
     *
     * @param date
     * @return
     */
    public static Calendar getCalendarByDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar selectedDate = Calendar.getInstance();
        int[] select = TimeUtils.getHourMinByDate(date);
        selectedDate.set(0, 0, 0, select[0], select[1]);
        return selectedDate;
    }

    /**
     * 格式化时间
     *
     * @param format
     * @return
     */
    public static String parse(String format) {
        return parse(null, format);
    }

    /**
     * 格式化时间
     *
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
     * 将时间转换为时间戳  1551800031 - 1551713631 = 86400
     */
    public static long dateToStamp(String s) {
        long stamp = getCurrentTime();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMD_HM);
            Date date = simpleDateFormat.parse(s);
            stamp = date.getTime();
        } catch (ParseException e) {
            stamp += 86400;
            e.printStackTrace();
        }
        return stamp;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMD_HMS);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取年月
     *
     * @param date
     * @return
     */
    public static String getYearMonth(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YM);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取明天
     *
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
     *
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

        StringBuffer datePoor = new StringBuffer();
        if (day != 0) {
            datePoor.append(day).append("天");
        }
        if (hour != 0) {
            datePoor.append(hour).append("小时");
        }
        if (min != 0) {
            datePoor.append(min).append("分钟");
        }
        if (datePoor.length() == 0) {
            datePoor.append("0分钟");
        }
        return datePoor.toString();
    }

    /**
     * 判断今日是否是周一到周日
     *
     * @return
     */
    public static boolean isMonToThurs() {
        String today = getTodayInWeek();
        for (int i = 0; i < WEEK.length; i++) {
            if (today.equals(WEEK[i])) {
                return i < 4;
            }
        }
        return false;
    }

    /**
     * 获取今天是周几
     *
     * @return
     */
    public static String getTodayInWeek() {
        return parseDate2Week(new Date());
    }

    public static String parseDate2Week(Calendar calendar) {
        return parseDate2Week(calendar.getTime());
    }

    /**
     * 将日期格式化成星期
     * @param date
     * @return
     */
    public static String parseDate2Week(Date date) {
        SimpleDateFormat dateFm = new SimpleDateFormat(FORMAT_EE);
        return dateFm.format(date);
    }

    /**
     * 获取下周一日期
     *
     * @param date
     * @return
     */
    public static String getNextMonday(Date date) {
        //获得入参的日期
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        // 获得入参日期是一周的第几天
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        // 获得入参日期相对于下周一的偏移量（在国外，星期一是一周的第二天，所以下周一是这周的第九天）
        // 若入参日期是周日，它的下周一偏移量是1
        int nextMondayOffset = dayOfWeek == 1 ? 1 : 9 - dayOfWeek;

        // 增加到入参日期的下周一
        cd.add(Calendar.DAY_OF_MONTH, nextMondayOffset);
        return parse(cd.getTime(), FORMAT_YMD);
    }

    /**
     *  获取下周周几的日期
     * @param week
     * @return
     */
    public static String getNextWeek(String week) {
        Calendar cd = Calendar.getInstance();
        do {
            cd.add(Calendar.DAY_OF_WEEK, 1);
        } while (!week.equals(parseDate2Week(cd)));
        return parse(cd.getTime(), FORMAT_YMD);
    }
}
