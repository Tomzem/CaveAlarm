package com.tomzem.cavealarm;

import android.support.annotation.BinderThread;
import android.widget.ListView;

import com.search.baselibrary.base.BaseActivity;
import com.search.baselibrary.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tb_first_page)
    TitleBar mTbFirstPage;

    @BindView(R.id.lv_alarm_list)
    ListView mLvAlarmList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        mTbFirstPage
                .setLeftGone()
                .setTitleText(getResources().getString(R.string.app_name));
        List<String> stringList = new ArrayList<>();
        for (int i=0; i<10; i++){
            stringList.add("00:0" + i);
        }
        AlarmInfoAdapter alarmInfoAdapter = new AlarmInfoAdapter(stringList, this, R.layout.list_item_alarm_info);
        mLvAlarmList.setAdapter(alarmInfoAdapter);
    }
}
