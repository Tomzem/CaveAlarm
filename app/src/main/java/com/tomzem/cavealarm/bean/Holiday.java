package com.tomzem.cavealarm.bean;

/**
 * @author Tomze
 * @time 2019年03月11日 22:53
 * @desc 节假日类
 */
public class Holiday {
    private String date;
    private int type;  //类型 0 工作日 1 假日 2 节假日

    public Holiday(String date, int type) {
        this.date = date;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return date + ":" + type + "\n";
    }
}
