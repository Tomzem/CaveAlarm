package com.tomzem.cavealarm.utils;

import android.app.Activity;
import android.content.Context;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月11日 0:28
 * @desc 权限申请类
 */
public class PermissionUtils {
    public static void getPermission(final Activity activity){
        if (XXPermissions.isHasPermission(activity, Permission.Group.STORAGE)) {
            ConfigManager.isHaveReadExternalStorage = true;
            ConfigManager.isHaveReadPhoneState = true;
            ConfigManager.isHaveWriteExternalStorage = true;
        }else{
            XXPermissions.with(activity)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.READ_PHONE_STATE,Permission.READ_EXTERNAL_STORAGE) //支持请求6.0悬浮窗权限8.0请求安装权限 Permission.SYSTEM_ALERT_WINDOW,
                    .permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            setPermissionState(granted, true);
                            if (!isAll) {
                                getPermission(activity);
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            setPermissionState(denied, false);
//                            ToastUtils.show("权限申请失败，退出程序");
//                            finish();
                        }
                    });
        }
    }

    private static void setPermissionState (List<String> permissions, boolean isHave) {
        for (String permission : permissions) {
            if ("android.permission.READ_EXTERNAL_STORAGE".equals(permission)) {
                ConfigManager.isHaveReadExternalStorage = isHave;
            } else if ("android.permission.READ_PHONE_STATE".equals(permission)) {
                ConfigManager.isHaveReadPhoneState = isHave;
            } else if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(permission)) {
                ConfigManager.isHaveWriteExternalStorage = isHave;
            }
        }
    }
}
