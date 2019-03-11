package com.tomzem.cavealarm.bean;

import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月11日 23:06
 * @desc 解析节假日json用
 */
public class JsonMonth {
    private int month;
    private int year;
    private List<Holiday> days;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Holiday> getDays() {
        return days;
    }

    public void setDays(List<Holiday> days) {
        this.days = days;
    }
}
