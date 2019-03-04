package com.tomzem.cavealarm.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.search.baselibrary.base.BaseActivity;
import com.search.baselibrary.base.MessageEventBus;
import com.search.baselibrary.utils.JumpUtil;
import com.search.baselibrary.widget.TitleBar;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.application.AlarmInfoAdapter;
import com.tomzem.cavealarm.bean.Alarm;
import com.tomzem.cavealarm.eventbus.RefreshAlarmListEvent;
import com.tomzem.cavealarm.helper.AlarmHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.tb_first_page)
    TitleBar mTbFirstPage;

    @BindView(R.id.lv_alarm_list)
    ListView mLvAlarmList;

    @BindView(R.id.btn_add_alarm)
    Button mBtnAddAlarm;

    private AlarmInfoAdapter mAlarmInfoAdapter;
    private List<Alarm> mAlarmList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mTbFirstPage
                .setLeftGone()
                .setTitleText(getResources().getString(R.string.app_name));

        mAlarmInfoAdapter = new AlarmInfoAdapter(new ArrayList<Alarm>(), this,
                R.layout.list_item_alarm_info);
        mLvAlarmList.setAdapter(mAlarmInfoAdapter);
        mLvAlarmList.setOnItemClickListener(this);

        mBtnAddAlarm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mAlarmList = new ArrayList<>();
        updateAlarmList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_alarm:
                JumpUtil.overlay(this, CreateAlarmActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void updateAlarmList() {
        mAlarmList.clear();
        mAlarmList.addAll(AlarmHelper.getAlarmHelper().getAllAlarm());
        sortAlarm();
        if (mAlarmList.size() > 0) {
            mAlarmInfoAdapter.onDataChange(mAlarmList);
        }
    }

    /**
     * 对于闹钟进行排序
     */
    private void sortAlarm() {
        Collections.sort(mAlarmList, new Comparator<Alarm>() {
            @Override
            public int compare(Alarm alarm1, Alarm alarm2) {
                int result = 0;
                int time1 = time2Int(alarm1);
                int time2 = time2Int(alarm2);
                if (time1 < time2) {
                    result = -1;
                } else {
                    result = 1;
                }
                return result;
            }

            private int time2Int(Alarm alarm) {
                return Integer.parseInt(alarm.getRingHour()) * 60 + Integer.parseInt(alarm.getRingMin());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageEventBus messageEvent) {
        if (messageEvent instanceof RefreshAlarmListEvent) {
            updateAlarmList();
        }
    }

    @Override
    protected void readyDestroy() {
        super.readyDestroy();
        EventBus.getDefault().unregister(this);
    }
}
