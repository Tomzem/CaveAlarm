package com.tomzem.cavealarm.application;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.view.View;

import com.search.baselibrary.utils.ParseUtils;
import com.search.baselibrary.utils.ToastUtils;
import com.search.baselibrary.widget.CheckImage;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.bean.Alarm;
import com.tomzem.cavealarm.helper.AlarmHelper;
import com.tomzem.cavealarm.utils.AppConstants;
import com.tomzem.cavealarm.utils.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author:Tomze
 * date:2019/2/26 18:59
 * description:
 */
public class AlarmInfoAdapter extends CommonAdapter<Alarm> {

    public AlarmInfoAdapter(List<Alarm> list, Context context, int resID) {
        super(list, context, resID);
    }

    @Override
    public void fillData(final int position, CommonViewHolder holder) {
        final Context mContext = mContextRef.get();
        final Alarm alarm = mDatas.get(position);

        holder.setText(R.id.tv_alarm_time, alarm.getRingHour() + ":" + alarm.getRingMin());
        final CheckImage checkImage = holder.getView(R.id.btn_is_on);

        String cycle = ParseUtils.getParseById(mContext.getResources().getStringArray(R.array.ring_cycle), alarm.getRingCycle());
        StringBuffer state = new StringBuffer();
        switch (alarm.getCurrentState()) {
            case AppConstants.ALARM_CLOSE:
                checkImage.setStateImage(R.drawable.ic_alarm_not_ring);
                checkImage.setCurrentState(false);
                state.append("未开启");
                break;
            case AppConstants.ALARM_RINGING:
                checkImage.setStateImage(R.drawable.ic_alarm_ring);
                checkImage.setCurrentState(true);
                state.append(cycle).append("| 正在响铃...");
                break;
            case AppConstants.ALARM_OPEN:
            case AppConstants.ALARM_RING_DELAY:
                checkImage.setStateImage(R.drawable.ic_alarm_ring);
                checkImage.setCurrentState(true);
                state.append(cycle)
                        .append("| 3分钟")
                        .append(mContext.getResources().getString(R.string.text_next_ring));
                break;
        }
        holder.setText(R.id.tv_alarm_state, state.toString());



        checkImage.addChangedListener(new CheckImage.OnChangedListener() {
            @Override
            public void onCheckChanged(View v, boolean state) {
                ToastUtils.show("点击 : " + alarm.getRingTime());
                alarm.setUpdateTime(TimeUtils.getCurrentTime());
                if (state) {
                    alarm.setCurrentState(AppConstants.ALARM_OPEN);
                } else {
                    switch(alarm.getCurrentState()) {
                        case AppConstants.ALARM_CLOSE:
                            break;
                        case AppConstants.ALARM_RINGING:
                            alarm.setCurrentState(AppConstants.ALARM_CLOSE);
                            //TODO:关闭当前响铃
                            break;
                        case AppConstants.ALARM_OPEN:
                        case AppConstants.ALARM_RING_DELAY:
                            alarm.setCurrentState(AppConstants.ALARM_CLOSE);
                            //TODO:关闭下次响铃
                            break;
                    }
                }
                if (AlarmHelper.getAlarmHelper().updateAlarm(alarm) > 0) {
                    ToastUtils.show("闹钟状态已修改");
                    onDataChange(mDatas);
                } else {
                    checkImage.setCheckedNoReturn(!state);
                }
            }
        });
    }
}
