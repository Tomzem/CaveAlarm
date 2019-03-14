package com.tomzem.cavealarm.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.tomzem.cavealarm.bean.Holiday;
import com.tomzem.cavealarm.bean.JsonMonth;
import com.tomzem.cavealarm.bean.ResultCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.tomzem.cavealarm.utils.AppConstants.DAY_WORK_TYPE;

/**
 * @author Tomze
 * @time 2019年03月03日 22:27
 * @desc 获取节假日工具类
 * 如果Assets文件中存在当前年份的节假日信息，且与data文件相同，则不进行进行处理
 * 如果Assets文件中存在当前年份的节假日信息 与data文件中的不同, 贼将assets同步到data中
 * 如果assets中没有，data中有，则不处理
 * 如果assets中没有，data中也没有，则进行下载
 */
public class HolidayUtils {

    private List<Holiday> mHolidayList;

    public HolidayUtils(Context context) {
        mHolidayList = loadHoliday(context);
    }

    /**
     * 判断日期是否为节假日
     *
     * @param date
     * @return
     */
    public boolean isHoliday(String date) {
        if (mHolidayList == null) {
            return false;
        }
        for (Holiday holiday : mHolidayList) {
            if (date.equals(holiday.getDate())) {
                return holiday.getType() != DAY_WORK_TYPE;
            }
        }
        return false;
    }

    /**
     * 获取某天的类型
     * @param date
     * @return 默认工作日
     */
    public int getDayType(String date) {
        if (mHolidayList == null) {
            return DAY_WORK_TYPE;
        }
        for (Holiday holiday : mHolidayList) {
            if (date.equals(holiday.getDate())) {
                return holiday.getType();
            }
        }
        return DAY_WORK_TYPE;
    }

    /**
     * 获取下一个工作日或者休假日
     *
     * @param type 0 工作日   1 双休日  2节假日
     * @return 2019-12-12
     */
    public String nextTypeDay(int type) {
        String day = TimeUtils.parse(TimeUtils.FORMAT_YMD);
        boolean isBeforeDay = true;
        for (Holiday holiday : mHolidayList) {
            if (isBeforeDay && !holiday.getDate().equals(day)) {
                continue;
            }
            if (holiday.getDate().equals(day)) {
                isBeforeDay = false;
                continue;
            }
            if (holiday.getType() == type || (type +1) == holiday.getType()) {
                day = holiday.getDate();
                break;
            }
        }
        return day;
    }

    /**
     * 从网站上获取假期日期 连休不准
     *
     * @param context
     */
    public static void getHolidayByInternet(Context context) {
        // 如果文件中存在当年的信息，直接保存
        String holidayByAssets = getHolidayByAssets(context);
        String holidayByFile = getHolidayByFile(context);
        if (!"".equals(holidayByAssets) && !holidayByAssets.equals(holidayByFile)) {
            saveHoliday(context, holidayByAssets);
        }
        if (isHaveCache(context)) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(AppConstants.HOLIDAY_API + TimeUtils.parse(TimeUtils.FORMAT_YY));
            URLConnection URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bufr = new BufferedReader(isr);
                String str;
                while ((str = bufr.readLine()) != null) {
                    stringBuffer.append(str.toString());
                }
                bufr.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parseJsonHoliday(context, stringBuffer.toString());
    }

    /**
     * 解析网站json数据
     *
     * @param context
     * @param json
     */
    private static void parseJsonHoliday(Context context, String json) {
        Gson gson = new Gson();
        ResultCode resultCode = gson.fromJson(json, ResultCode.class);
        StringBuffer stringBuffer = new StringBuffer();
        if (resultCode != null) {
            if (resultCode.getCode() == 1) {
                List<JsonMonth> jsonMonths = resultCode.getData();
                for (JsonMonth jsonMonth : jsonMonths) {
                    List<Holiday> holidays = jsonMonth.getDays();
                    for (Holiday holiday : holidays) {
                        stringBuffer.append(holiday.toString());
                    }
                }
            }
        }

        //如果信息与本地不同 则加载本地信息，如果本地无信息 则加载网络
        String holidayByFile = getHolidayByAssets(context);
        if ("".equals(holidayByFile) || holidayByFile.equals(stringBuffer.toString())) {
            saveHoliday(context, stringBuffer.toString());
        } else {
            saveHoliday(context, holidayByFile);
        }
    }

    /**
     * 获取项目中holiday信息， 根据网站返回信息修改
     *
     * @param context
     */
    public static String getHolidayByAssets(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(TimeUtils.parse(TimeUtils.FORMAT_YY) + ".txt");
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader bf = new BufferedReader(isr);
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static String getHolidayByFile(Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            //设置将要打开的存储文件名称
            in = context.openFileInput(TimeUtils.parse(TimeUtils.FORMAT_YY) + ".txt");
            //FileInputStream -> InputStreamReader ->BufferedReader
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            //读取每一行数据，并追加到StringBuilder对象中，直到结束
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 保存网站信息
     *
     * @param context
     * @param s
     */
    public static void saveHoliday(Context context, String s) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(TimeUtils.parse(TimeUtils.FORMAT_YY) + ".txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(s);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载holiday信息
     *
     * @param context
     * @return
     */
    public static List<Holiday> loadHoliday(Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        List<Holiday> holidays = new ArrayList<>();
        try {
            //设置将要打开的存储文件名称
            in = context.openFileInput(TimeUtils.parse(TimeUtils.FORMAT_YY) + ".txt");
            //FileInputStream -> InputStreamReader ->BufferedReader
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            //读取每一行数据，并追加到StringBuilder对象中，直到结束
            while ((line = reader.readLine()) != null) {
                content.append(line);
                String[] holiday = line.split(":");
                if (holiday.length < 2) {
                    continue;
                }
                holidays.add(new Holiday(holiday[0], Integer.valueOf(holiday[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return holidays;
    }

    /**
     * 判断是否已经有了缓存
     *
     * @param context
     * @return
     */
    private static boolean isHaveCache(Context context) {
        try {
            FileInputStream in = context.openFileInput(TimeUtils.parse(TimeUtils.FORMAT_YY) + ".txt");
            if (in != null) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
