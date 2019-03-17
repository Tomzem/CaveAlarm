package com.search.baselibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * @author Tomze
 * @time 2019年03月17日 11:42
 * @desc 根据字数多少变换字体大小
 */
public class AutoFitTextView extends AppCompatTextView {
    private float mDefaultTextSize;
    private Paint mTextPaint;

    public AutoFitTextView(Context context) {
        this(context ,null);
    }

    public AutoFitTextView(Context context,  AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public AutoFitTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr();
    }

    private void initAttr() {
        mTextPaint = new Paint();
        mTextPaint.set(getPaint());
        mDefaultTextSize = getTextSize();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        refitText(text.toString(),getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refitText(getText().toString(),getWidth());
    }

    public void refitText(String text, int textWidth){
        if(textWidth > 0){
            int availableTextWidth = textWidth - getPaddingLeft() - getPaddingRight();
            float tsTextSize = mDefaultTextSize;
            mTextPaint.setTextSize(tsTextSize);
            float length = mTextPaint.measureText(text);
            while (length > availableTextWidth) {
                tsTextSize--;
                mTextPaint.setTextSize(tsTextSize);
                length = mTextPaint.measureText(text);
            }
            setTextSize(TypedValue.COMPLEX_UNIT_PX,tsTextSize);
            invalidate();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}