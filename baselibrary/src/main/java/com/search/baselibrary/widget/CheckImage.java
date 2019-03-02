package com.search.baselibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.search.baselibrary.R;


/**
 * author:Tomze
 * date:2019/2/27 10:24
 * description: 自定义开关按钮
 */
public class CheckImage extends android.support.v7.widget.AppCompatImageView {

    private boolean CurrentState = false;
    private int[] StateImage = {R.drawable.ic_alarm_ring, R.drawable.ic_alarm_not_ring};
    private boolean isLoadView = true;

    private OnChangedListener onChangedListener;

    public CheckImage(Context context) {
        this(context, null);
    }

    public CheckImage(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CheckImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setChecked(CurrentState);
    }

    /**
     * 设置当前状态
     * @param checked
     */
    public void setChecked(boolean checked) {
        if (!isLoadView && checked && CurrentState) {
            return;
        }
        CurrentState = !CurrentState;
        changeStateImage();
        // 判断是否为加载View时设置默认值
        if (!isLoadView) {
            onChangedListener.onCheckChanged(this, CurrentState);
        } else {
            isLoadView = false;
        }
    }

    public void setChecked() {
        setChecked(!CurrentState);
    }

    /**
     * 状态切换后更新图片
     */
    private void changeStateImage() {
        if (CurrentState) {
            setStateImage(StateImage[0]);
        } else {
            setStateImage(StateImage[1]);
        }
    }

    /**
     *  获取当前状态
     * @return
     */
    public boolean getChecked() {
        return CurrentState;
    }

    /**
     * 设置图片资源
     * @param resourceID
     */
    public void setStateImage(int resourceID) {
        this.setImageDrawable(getResources().getDrawable(resourceID));
    }

    /**
     * 设置图片资源
     * @param images
     */
    public void setStateImages(int[] images) {
        if (images.length >= 2){
            for (int i=1; i<2; i++) {
                this.StateImage[i] = images[i];
            }
        }
    }

    public void addChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    public interface OnChangedListener {
        void onCheckChanged(View v, boolean state);
    }
}
