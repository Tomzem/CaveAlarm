package com.tomzem.cavealarm.ui;

import android.widget.ListView;

import com.search.baselibrary.base.BaseActivity;
import com.search.baselibrary.widget.TitleBar;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.application.AlarmInfoAdapter;
import com.tomzem.cavealarm.bean.User;
import com.tomzem.cavealarm.helper.UserDaoHelper;

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
        for (int i=0; i<3; i++){
            stringList.add("00:0" + i);
        }
        AlarmInfoAdapter alarmInfoAdapter = new AlarmInfoAdapter(stringList, this, R.layout.list_item_alarm_info);
        mLvAlarmList.setAdapter(alarmInfoAdapter);

        User user = new User();
        user.setName("hello");
        UserDaoHelper.getInstance().insertUser(user);
    }
}
