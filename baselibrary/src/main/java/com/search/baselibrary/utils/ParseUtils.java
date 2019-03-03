package com.search.baselibrary.utils;

import com.search.baselibrary.bean.MenuResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月03日 15:49
 * @desc
 */
public class ParseUtils {

    /**
     *  解析设置菜单项
     * @param results
     * @return
     */
    public static List<MenuResult> parseMenuResult(String[] results) {
        List<MenuResult> menuResults = new ArrayList<>();
        if (results == null) {
            return menuResults;
        }
        for (String result : results){
            String[] item = result.split(":");
            if (item.length == 4){
                MenuResult menuResult = new MenuResult(Integer.valueOf(item[0]), item[1], Integer.valueOf(item[2]),Integer.valueOf(item[2]));
                menuResults.add(menuResult);
            }
        }
        return menuResults;
    }

    public static String[] parseMenuResults(String[] results) {
        String[] menuResults = new String[results.length];
        if (results == null) {
            return menuResults;
        }
        int i = 0;
        for (String result : results){
            String[] item = result.split(":");
            if (item.length == 4){
                menuResults[i++] = item[1];
            }
        }
        return menuResults;
    }

    /**
     * 根据id 获取菜单
     * @param results
     * @param id
     * @return
     */
    public static String getParseById(String[] results, int id) {
        if (results == null) {
            return "";
        }
        for (String result : results){
            String[] item = result.split(":");
            if (item.length > 2){
                if (item[0].equals(id + "")) {
                    return item[1];
                }
            }
        }
        return "";
    }
}
