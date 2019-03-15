package com.tomzem.cavealarm.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.search.baselibrary.base.BaseActivity;
import com.search.baselibrary.bean.MenuResult;
import com.search.baselibrary.utils.DialogUtils;
import com.search.baselibrary.utils.ParseUtils;
import com.search.baselibrary.utils.ToastUtils;
import com.search.baselibrary.widget.RingConfigItem;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.bean.Alarm;
import com.tomzem.cavealarm.eventbus.RefreshAlarmListEvent;
import com.tomzem.cavealarm.helper.AlarmHelper;
import com.tomzem.cavealarm.utils.HolidayUtils;
import com.tomzem.cavealarm.utils.NextRingUtils;
import com.tomzem.cavealarm.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.tomzem.cavealarm.utils.AppConstants.DAY_HOLIDAY_TYPE;
import static com.tomzem.cavealarm.utils.AppConstants.DAY_SXIU_TYPE;
import static com.tomzem.cavealarm.utils.AppConstants.DAY_WORK_TYPE;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_CEASE_10;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_CEASE_7;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_CEASE_8;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_CEASE_9;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_EVERYDAY;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_HOLIDAY;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_ONCE;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_SELF;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_WEEK;
import static com.tomzem.cavealarm.utils.AppConstants.MENU_ALARM_WORK_DAY;

public class CreateAlarmActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_select_time)
    TextView mTpSelectTime;

    @BindView(R.id.tv_next_ring)
    TextView mTvNextRing;

    @BindView(R.id.btn_create_alarm)
    Button mBtnCreateAlarm;

    @BindView(R.id.rci_ring_cycle)
    RingConfigItem mRciRingCycle;

    @BindView(R.id.rci_ring_note)
    RingConfigItem mRciRingNote;

    private Context mContext;
    private Date mSelectTime;
    private MenuResult mCurrentMenuResult;
    private List<MenuResult> mMenuResult;
    private List<MenuResult> mMenuResultSelf;
    private int[] currentHourMin;
    private int[] selectHourMin;
    private HolidayUtils holidayUtils;

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
        mRciRingCycle.setOnClickListener(this);
        mRciRingNote.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        holidayUtils = new HolidayUtils(mContext);
        mTpSelectTime.setText(TimeUtils.getCurrentHourMin());
        mCurrentMenuResult = mRciRingCycle.getMenuResult();
    }

    @Override
    public void onClick(View v) {
        if (v == mTpSelectTime) {
            showPicker();
        } else if (v == mBtnCreateAlarm) {
            createAlarm();
        } else if (v == mRciRingCycle) {
            chooseAlarmCycle(R.array.ring_cycle);
        }
    }

    private void chooseAlarmCycle(int resID) {
        final String[] ringCycles = getResources().getStringArray(resID);
        mMenuResult = new ArrayList<>();
        mMenuResult.addAll(ParseUtils.parseMenuResult(ringCycles));
        AlertDialog.Builder singleDialog = new AlertDialog.Builder(mContext);
        singleDialog.setItems(ParseUtils.parseMenuResults(ringCycles), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuResult newResult = mMenuResult.get(which);
                if (mCurrentMenuResult.getId() == MENU_ALARM_SELF || mCurrentMenuResult.getId() != newResult.getId()) {
                    if (newResult.getId() == 6) {
                        chooseAlarmCycle(R.array.ring_cycle_cease);
                        dialog.dismiss();
                        return;
                    }
                    for (MenuResult menuResult : mMenuResult) {
                        menuResult.setChoose(newResult.getId() == menuResult.getId());
                    }
                    if (newResult.getId() == MENU_ALARM_SELF) {
                        // 选择自定义
                        chooseAlarmCycleSelf();
                        dialog.dismiss();
                        return;
                    }
                    mRciRingCycle.setMenuResult(mMenuResult);
                }
                calculateNextRing();
                dialog.dismiss();
            }
        });
        singleDialog.create().show();
    }

    /**
     * 选择自定义日期
     */
    private void chooseAlarmCycleSelf() {
        final String[] ringCycles = getResources().getStringArray(R.array.ring_cycle_self);
        mMenuResultSelf = new ArrayList<>();
        mMenuResultSelf.addAll(ParseUtils.parseMenuResult(ringCycles));
        final boolean mChoseSts[] = {false, false, false, false, false, false, false};
        AlertDialog.Builder multiDialog = new AlertDialog.Builder(mContext);
        multiDialog.setMultiChoiceItems(ParseUtils.parseMenuResults(ringCycles), mChoseSts, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    mMenuResultSelf.get(which).setChoose(true);
                } else {
                    mMenuResultSelf.get(which).setChoose(false);
                }
            }
        });
        multiDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mMenuResult != null && mMenuResult.size() > MENU_ALARM_SELF) {
                    mMenuResult.get(MENU_ALARM_SELF).setMenuResultSelf(mMenuResultSelf);
                }
                mRciRingCycle.setMenuResult(mMenuResult);
                calculateNextRing();
                dialog.dismiss();
            }
        });
        multiDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                chooseAlarmCycle(R.array.ring_cycle);
            }
        });
        multiDialog.create().show();


    }

    private void createAlarm() {
        final Alarm alarm = new Alarm();
        String[] split = mTpSelectTime.getText().toString().split(":");
        alarm.setRingHour(split[0]);
        alarm.setRingMin(split[1]);
        alarm.setAlarmNote(mRciRingNote.getMenuNote());
        alarm.setRingCycle(mRciRingCycle.getMenuResult().getId());
        if (alarm.getCreateTime() == null) {
            alarm.setCreateTime(TimeUtils.getCurrentTime());
        } else {
            alarm.setUpdateTime(TimeUtils.getCurrentTime());
        }

        StringBuffer message = new StringBuffer();
        message.append("响铃时间:").append(mTpSelectTime.getText().toString() + "\n")
                .append("重复周期:").append(mRciRingCycle.getMenuResultString() + "\n");
        if (!TextUtils.isEmpty(alarm.getAlarmNote())) {
            message.append("响铃备注:").append(alarm.getAlarmNote());
        }
        DialogUtils.showAlert(mContext, getResources().getString(R.string.create_ring_dialog_title),
                message.toString(), "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long isAdd = AlarmHelper.getAlarmHelper().addAlarm(alarm);
                        if (isAdd != -1) {
                            ToastUtils.show(getResources().getString(R.string.create_success));
                            EventBus.getDefault().post(new RefreshAlarmListEvent());
                            finish();
                        } else {
                            ToastUtils.show(getResources().getString(R.string.create_fail));
                        }
                    }
                },
                "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void showPicker() {
        TimePickerView picker = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTpSelectTime.setText(TimeUtils.parse(date, TimeUtils.FORMAT_HM));
                mSelectTime = date;
                calculateNextRing();
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
                .setDate(TimeUtils.getCalendarByDate(mSelectTime))
                .setLabel("", "", "", "", "", "")
                .isDialog(false)//是否显示为对话框样式
                .build();
        picker.show();
    }

    /**
     * 计算多久后响铃时间
     */
    private void calculateNextRing() {
        long ringTime = 0;
        currentHourMin = TimeUtils.getHourMinByDate(new Date());
        String[] selectHourMinStr = mTpSelectTime.getText().toString().split(":");
        selectHourMin = new int[selectHourMinStr.length];
        for (int i = 0; i < selectHourMin.length; i++) {
            selectHourMin[i] = Integer.parseInt(selectHourMinStr[i]);
        }
        if (selectHourMin.length == 2 && currentHourMin.length == 2) {
            NextRingUtils nextRingUtils = NextRingUtils.getNextRingUtils(currentHourMin, selectHourMin);
            switch (mRciRingCycle.getMenuResult().getId()) {
                case MENU_ALARM_ONCE:
                case MENU_ALARM_EVERYDAY:
                    ringTime = nextRingUtils.ringInTodayOrNextDay();
                    break;
                case MENU_ALARM_CEASE_8:
                case MENU_ALARM_CEASE_10:
                case MENU_ALARM_WORK_DAY:
                    // 工作日+下周六 工作日+下周日 工作日
                    if (!holidayUtils.isHoliday(TimeUtils.parse(TimeUtils.FORMAT_YMD))
                            && nextRingUtils.isRingToday()) {
                        // 判断今日是否是工作日 且 是否在今日响铃
                        ringTime = nextRingUtils.ringInTodayOrNextDay();
                    } else {
                        // 在下一个工作日响铃
                        ringTime = nextRingUtils.getAssignDayRingTime(holidayUtils.nextTypeDay(DAY_WORK_TYPE));
                    }
                    break;
                case MENU_ALARM_HOLIDAY:
                    if (holidayUtils.isHoliday(TimeUtils.parse(TimeUtils.FORMAT_YMD))
                            && nextRingUtils.isRingToday()) {
                        // 判断今日是否是双休日 且 是否在今日响铃
                        ringTime = nextRingUtils.ringInTodayOrNextDay();
                    } else {
                        // 在下一个双休日响铃
                        ringTime = nextRingUtils.getAssignDayRingTime(holidayUtils.nextTypeDay(DAY_SXIU_TYPE));
                    }
                    break;
                case MENU_ALARM_WEEK:
                    // 判断今天是周几 周一到周四直接调用 ringInTodayOrNextDay
                    if (TimeUtils.isMonToThurs()) {
                        ringTime = nextRingUtils.ringInTodayOrNextDay();
                        break;
                    } else if (getResources().getString(R.string.text_Friday).equals(TimeUtils.getTodayInWeek())) {
                        // 周五 判断当天是否响铃
                        if (nextRingUtils.isRingToday()) {
                            ringTime = nextRingUtils.ringInTodayOrNextDay();
                            break;
                        }
                    }
                    //周五不响 周六 周天  下周一响
                    ringTime = nextRingUtils.getAssignDayRingTime(TimeUtils.getNextMonday(new Date()));
                    break;
                case MENU_ALARM_SELF:
                    MenuResult firstResult = null;
                    for (MenuResult menuResult : mMenuResultSelf) {
                        // 将一周中第一个选中的保存
                        if (firstResult == null && menuResult.isChoose()) {
                            firstResult = menuResult;
                        }
                        if (!TimeUtils.getTodayInWeek().equals(menuResult.getResult())) {
                            continue;
                        }
                        if (nextRingUtils.isRingToday()) {
                            ringTime = nextRingUtils.ringInTodayOrNextDay();
                            break;
                        } else {
                            if (menuResult.isChoose()) {
                                ringTime = nextRingUtils.getAssignDayRingTime(TimeUtils.getNextWeek(menuResult.getResult()));
                                continue;
                            }
                        }
                    }
                    // 如果在下次响铃在当天之前
                    if (ringTime == 0) {
                        ringTime = nextRingUtils.getAssignDayRingTime(TimeUtils.getNextWeek(firstResult.getResult()));
                    }
                    break;
                case MENU_ALARM_CEASE_7:
                case MENU_ALARM_CEASE_9:
                    if (!holidayUtils.isHoliday(TimeUtils.parse(TimeUtils.FORMAT_YMD))
                            && (nextRingUtils.isRingToday() || !holidayUtils.isHoliday(TimeUtils.getNextDay()))) {
                        // 判断 今日是工作日 且 (在今日响铃 或者 明天是一个工作日)
                        ringTime = nextRingUtils.ringInTodayOrNextDay();
                        break;
                    }
                    // 今天不是工作日  type =1||2
                    if (!(holidayUtils.getDayType(TimeUtils.parse(TimeUtils.FORMAT_YMD)) == DAY_HOLIDAY_TYPE)) {
                        // 不是法定节假日
                        if (mRciRingCycle.getMenuResult().getId() == MENU_ALARM_CEASE_9
                                && getResources().getString(R.string.text_Sunday).equals(TimeUtils.getTodayInWeek())) {
                            // 选择本周星期天  且 今天是星期天
                            if (nextRingUtils.isRingToday() || !holidayUtils.isHoliday(TimeUtils.getNextDay())) {
                                // 今天响铃  或者明天是工作日
                                ringTime = nextRingUtils.ringInTodayOrNextDay();
                                break;
                            }
                        }
                        if (mRciRingCycle.getMenuResult().getId() == MENU_ALARM_CEASE_7
                                && getResources().getString(R.string.text_Saturday).equals(TimeUtils.getTodayInWeek())) {
                            // 选择本周星期6  且 今天是星期6
                            if (nextRingUtils.isRingToday()) {
                                ringTime = nextRingUtils.ringInTodayOrNextDay();
                                break;
                            }
                        }
                    }
                    // 如果今天是法定节假日  在下一个工作日响铃
                    ringTime = nextRingUtils.getAssignDayRingTime(holidayUtils.nextTypeDay(DAY_WORK_TYPE));
                    break;
            }
            setRingPoorText(ringTime != 0 ? ringTime : TimeUtils.getCurrentTime());
        }
    }

    /**
     * 设置响铃时间差
     *
     * @param ringTime
     */
    public void setRingPoorText(long ringTime) {
        StringBuffer text = new StringBuffer();
        text.append(TimeUtils.getDatePoor(ringTime, TimeUtils.getCurrentTime()))
                .append(getResources().getString(R.string.text_next_ring));
        mTvNextRing.setText(text.toString());
    }

}
