package com.tomzem.cavealarm.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.search.baselibrary.base.BaseActivity;
import com.search.baselibrary.utils.ToastUtils;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.bean.Alarm;
import com.tomzem.cavealarm.eventbus.RefreshAlarmListEvent;
import com.tomzem.cavealarm.helper.AlarmHelper;
import com.tomzem.cavealarm.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;

public class CreateAlarmActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_select_time)
    TextView mTpSelectTime;

    @BindView(R.id.btn_create_alarm)
    Button mBtnCreateAlarm;

    private Context mContext;
    private Date mSelectDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_alarm;
    }

    @Override
    protected void initView() {
        super.initView();
        mContext = this;
        mTpSelectTime.setOnClickListener(this);
        mBtnCreateAlarm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mTpSelectTime.setText(TimeUtils.getCurrentHourMin());
    }

    @Override
    public void onClick(View v) {
        if (v == mTpSelectTime) {
            showPicker();
        } else if (v == mBtnCreateAlarm){
            createAlarm();
        }
    }

    private void createAlarm() {
        Alarm alarm = new Alarm();
        String[] split = mTpSelectTime.getText().toString().split(":");
        alarm.setRingHour(split[0]);
        alarm.setRingMin(split[1]);
        long isAdd = AlarmHelper.getAlarmHelper().addAlarm(alarm);
        if (isAdd != -1) {
            ToastUtils.show(getResources().getString(R.string.create_success));
            EventBus.getDefault().post(new RefreshAlarmListEvent());
            finish();
        } else {
            ToastUtils.show(getResources().getString(R.string.create_fail));
        }
    }

    private void showPicker() {
        TimePickerView picker = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTpSelectTime.setText(TimeUtils.parse(date, TimeUtils.FORMAT_HM));
                mSelectDate = date;
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(38)//滚轮文字大小
                .setTitleSize(33)//标题文字大小
                .setSubCalSize(30)
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.color_15_white)//标题文字颜色
                .setSubmitColor(R.color.color_15_white)//确定按钮文字颜色
                .setCancelColor(R.color.color_15_white)//取消按钮文字颜色
                .setTitleBgColor(0xFF778899)//标题背景颜色 Night mode
                .setBgColor(0xFF708090)//滚轮背景颜色 Night mode
                .setDate(TimeUtils.getCalendarByDate(mSelectDate))
                .setLabel("", "", "", "", "", "")
                .isDialog(false)//是否显示为对话框样式
                .build();
        picker.show();
    }
}
