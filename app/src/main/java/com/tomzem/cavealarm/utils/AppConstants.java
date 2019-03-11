package com.tomzem.cavealarm.utils;

/**
 * author : gxl
 * email : 1739037476@qq.com
 * create data : 2018/12/20
 * Describe  : 常用常量
 */
public class AppConstants {

    /**
     * 数据库目录
     */
    public static final String DB_DIR = "CaveDb";

    /**
     * log文件目录
     */
    public static final String LOG_DIR = "CaveLog";

    /**
     * 存放数据的主目录
     */
    public static final String ROOT_DIR = "CaveAlarm";

    /**
     * 数据库的名称
     */
    public static final String DB_NAME = "alarm.hello-ketty";

    public static final String[] DIRs = {
            ROOT_DIR,
            DB_DIR,
            LOG_DIR
    };
    //    -1关闭 0开启 1响铃 2延迟响铃
    public static final int ALARM_CLOSE = -1;
    public static final int ALARM_OPEN = 0;
    public static final int ALARM_RINGING = 1;
    public static final int ALARM_RING_DELAY = 2;

    // 自定义在第5项
    public static final int MENU_ALARM_ONCE = 0;
    public static final int MENU_ALARM_EVERYDAY = 1;
    public static final int MENU_ALARM_WORK_DAY = 2;
    public static final int MENU_ALARM_HOLIDAY = 3;
    public static final int MENU_ALARM_WEEK = 4;
    public static final int MENU_ALARM_SELF = 5;
    public static final int MENU_ALARM_CEASE = 6;

    // 获取万年历信息 + 年份 eg:2019.txt
    public static final String HOLIDAY_API = "http://www.mxnzp.com/api/holiday/list/year/";
}
