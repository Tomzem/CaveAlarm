package com.tomzem.cavealarm.bean;


import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月11日 23:03
 * @desc 解析节假日json用
 */
public class ResultCode {
    private int code;
    private String msg;
    private List<JsonMonth> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<JsonMonth> getData() {
        return data;
    }

    public void setData(List<JsonMonth> data) {
        this.data = data;
    }
}
