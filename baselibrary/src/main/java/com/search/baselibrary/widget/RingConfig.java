package com.search.baselibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Tomze
 * @time 2019年03月03日 11:45
 * @desc
 */
public class RingConfig extends LinearLayout{

    private Context mContext;

    public RingConfig(Context context) {
        this(context, null);
    }

    public RingConfig(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RingConfig(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
