package com.search.baselibrary.bean;

import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月03日 13:03
 * @desc 菜单中选择列表
 */
public class MenuResult {
    private int id;
    private String result;
    private boolean isChoose;
    private int pid;
    private List<MenuResult> menuResultSelf;

    public MenuResult(int id, String result, int isChoose, int pid) {
        this.id = id;
        this.result = result;
        this.isChoose = isChoose == 1;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<MenuResult> getMenuResultSelf() {
        return menuResultSelf;
    }

    public void setMenuResultSelf(List<MenuResult> menuResultSelf) {
        this.menuResultSelf = menuResultSelf;
    }
}
