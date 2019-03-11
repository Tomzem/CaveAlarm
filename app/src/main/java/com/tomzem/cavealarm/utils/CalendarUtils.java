package com.tomzem.cavealarm.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.tomzem.cavealarm.bean.Holiday;
import com.tomzem.cavealarm.bean.JsonMonth;
import com.tomzem.cavealarm.bean.ResultCode;
import com.tomzem.cavealarm.ui.MainActivity;

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

/**
 * @author Tomze
 * @time 2019年03月03日 22:27
 * @desc
 */
public class CalendarUtils {

    /**
     * 从网站上获取假期日期 连休不准
     * @param context
     */
    public static void getHolidayByInternet(Context context) {
        if (isHaveCache(context)) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
//            URL url = new URL(AppConstants.HOLIDAY_API + TimeUtils.parse(TimeUtils.FORMAT_YY));
//            URLConnection URLconnection = url.openConnection();
//            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
//            int responseCode = httpConnection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                InputStream in = httpConnection.getInputStream();
//                InputStreamReader isr = new InputStreamReader(in);
//                BufferedReader bufr = new BufferedReader(isr);
//                String str;
//                while ((str = bufr.readLine()) != null) {
//                    stringBuffer.append(str.toString());
//                }
//                bufr.close();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parseJsonHoliday(context, stringBuffer.toString());
    }

    /**
     * 解析网站json数据
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

        String holidayByFile = getHolidayByFile(context);
        if (holidayByFile.equals(stringBuffer.toString())) {
            saveHoliday(context, stringBuffer.toString());
        } else {
            saveHoliday(context, holidayByFile);
        }
    }

    /**
     * 获取本地holiday信息， 根据网站返回信息修改
     * @param context
     */
    public static String getHolidayByFile(Context context) {
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

    /**
     * 保存网站信息
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
            String line = new String();
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
