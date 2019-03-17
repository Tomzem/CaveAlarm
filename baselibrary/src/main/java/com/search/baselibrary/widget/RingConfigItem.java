package com.search.baselibrary.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.search.baselibrary.R;
import com.search.baselibrary.bean.MenuResult;
import com.search.baselibrary.utils.ParseUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月03日 11:48
 * @desc
 */
public class RingConfigItem extends RelativeLayout {

    private TextView mTvMenuName;
    private AutoFitTextView mTvMenuResult;
    private ImageView mImgMenuRight;
    private EditText mEtMenuNote;

    private String mMenuName;
    private String mMenuHint;

    private int mRightType = 0;
    private String[] mStrResults;
    private MenuResult rightResult; //当前选择的结果

    public static final int TYPE_NONE = 0;
    public static final int TYPE_MENU = 1;
    public static final int TYPE_NOTE = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_NONE, TYPE_MENU, TYPE_NOTE})
    @interface RightType {
    }

    public RingConfigItem(Context context) {
        this(context, null);
    }

    public RingConfigItem(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RingConfigItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.wigget_ring_config_item, this);
        initView();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RingConfigItem);
        try {
            mRightType = ta.getInteger(R.styleable.RingConfigItem_rightType, TYPE_NONE);
            mMenuName = ta.getString(R.styleable.RingConfigItem_rightText);
            mMenuHint = ta.getString(R.styleable.RingConfigItem_rightHint);
            mStrResults = getResources().getStringArray(ta.getResourceId(R.styleable.RingConfigItem_rightResult, 0));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
        initData();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isFocusable()) {
            return;
        }
    }

    private void initView() {
        mTvMenuName = this.findViewById(R.id.tv_menu_name);
        mTvMenuResult = this.findViewById(R.id.tv_menu_result);
        mImgMenuRight = this.findViewById(R.id.img_menu_right);
        mEtMenuNote = findViewById(R.id.et_menu_note);
    }

    private void initData() {
        setRightType(mRightType);
        setMenuName(mMenuName);
        setMenuResult(mStrResults);
        setMenuHint(mMenuHint);
    }

    public void setMenuName(String name) {
        mTvMenuName.setText(name != null ? name : "");
    }

    public void setMenuHint(String hint) {
        mEtMenuNote.setHint(hint != null ? hint : "");
    }

    public String getMenuNote() {
        return mEtMenuNote.getText().toString().trim();
    }

    public MenuResult getMenuResult() {
        return rightResult != null ? rightResult : new MenuResult(0, "", 0, 0);
    }

    public String getMenuResultString() {
        return mTvMenuResult.getText().toString().trim();
    }

    public void setMenuResult(String[] results) {
        setMenuResult(ParseUtils.parseMenuResult(results));
    }

    public void setMenuResult(List<MenuResult> menuResult) {
        StringBuffer result = new StringBuffer();
        if (menuResult == null) {
            return;
        }
        if (menuResult.size() > 5) {
            // 判断是否有自定义日期
            List<MenuResult> menuResultSelf = menuResult.get(5).getMenuResultSelf();
            if (menuResultSelf != null && menuResultSelf.size() > 0) {
                for (MenuResult menu : menuResultSelf) {
                    if (menu.isChoose()) {
                        result.append(menu.getResult().trim()).append(",");
                    }
                }
                rightResult = menuResult.get(5);
                mTvMenuResult.setText(result.toString().substring(0, result.length() - 1));
                return;
            }
        }
        // 正常逻辑
        for (MenuResult menu : menuResult) {
            if (menu.isChoose()) {
                result.append(menu.getResult().trim());
                rightResult = menu;
                break;
            }
        }
        mTvMenuResult.setText(result.toString());
    }

    /**
     * 设置菜单显示类型
     */
    public void setRightType(@RightType int type) {
        switch (type) {
            case TYPE_NONE:
                mTvMenuResult.setVisibility(GONE);
                mImgMenuRight.setVisibility(GONE);
                mEtMenuNote.setVisibility(GONE);
                break;
            case TYPE_MENU:
                mTvMenuResult.setVisibility(VISIBLE);
                mImgMenuRight.setVisibility(VISIBLE);
                mEtMenuNote.setVisibility(GONE);
                break;
            case TYPE_NOTE:
                mTvMenuResult.setVisibility(GONE);
                mImgMenuRight.setVisibility(GONE);
                mEtMenuNote.setVisibility(VISIBLE);
                break;
        }
    }
}
