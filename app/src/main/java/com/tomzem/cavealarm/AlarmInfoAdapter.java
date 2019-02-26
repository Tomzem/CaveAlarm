package com.tomzem.cavealarm;

import android.content.Context;

import java.util.List;

/**
 * author:Tomze
 * date:2019/2/26 18:59
 * description:
 */
public class AlarmInfoAdapter extends CommonAdapter<String> {

    public AlarmInfoAdapter(List<String> list, Context context, int resID) {
        super(list, context, resID);
    }

    @Override
    public void fillData(int position, CommonViewHolder holder) {
        String text = mDatas.get(position);

        holder.setText(R.id.tv_alarm_time, text);
    }
}
