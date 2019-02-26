package com.tomzem.cavealarm;

import android.content.Context;

import com.search.baselibrary.base.BaseApplication;
import com.search.baselibrary.manager.SkinManager;

/**
 * author:Tomze
 * date:2019/2/26 16:21
 * description:
 */
public class AlarmApplication extends BaseApplication {

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        // 注册换肤模式
        SkinManager.getInstance().init(this);
    }

    public Context getContext() {
        return mContext;
    }
}
