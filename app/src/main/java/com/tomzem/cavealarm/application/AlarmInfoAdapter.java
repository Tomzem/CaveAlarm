package com.tomzem.cavealarm.application;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.view.View;

import com.search.baselibrary.utils.ToastUtils;
import com.search.baselibrary.widget.CheckImage;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.bean.Alarm;

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
        final Alarm alarm = mDatas.get(position);

        holder.setText(R.id.tv_alarm_time, alarm.getRingHour() + ":" + alarm.getRingMin());
        final CheckImage checkImage = holder.getView(R.id.btn_is_on);
        checkImage.addChangedListener(new CheckImage.OnChangedListener() {
            @Override
            public void onCheckChanged(View v, boolean state) {
                ToastUtils.show("点击 : " + alarm.getRingTime());
            }
        });
        checkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage.setChecked();
            }
        });
    }
}
