package com.bruce.ui.lsn18.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bruce.ui.R;

public class MenuContentLayout extends LinearLayout {

    private float mMaxTranslationX;
    private boolean isOpened;

    public MenuContentLayout(Context context) {
        this(context, null);
    }

    public MenuContentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuContentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MenuContentLayout);
            mMaxTranslationX = a.getDimension(R.styleable.MenuContentLayout_maxTranslationX, 0);
            a.recycle();
        }
    }

    public void setTouchY(float y, float percent) {
        Log.i("hzw", "MenuContentLayout >> y = " + y + ", percent = " + percent);
        isOpened = percent > 0.8f;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setPressed(false);
            boolean isHover = isOpened && y > view.getTop() && y < view.getBottom();
            Log.i("barry", "isHover:" + isHover);
            if (isHover) {
                view.setPressed(true);
            }
            changeViewOffset(view, y, percent);
        }
    }

    private void changeViewOffset(View view, float y, float slideOffset) {
        //偏移距离
        float translationX = 0;
        //
        int centerY = view.getTop() + view.getHeight() / 2;
        //控件中心点 局手指的距离
        float distance = Math.abs(y - centerY);
        float scale = distance / getHeight() * 4;//3   放大系数
        translationX = mMaxTranslationX - scale * mMaxTranslationX;
        Log.i("hzw", "translationX = " + translationX + ", y = " + y + ", distance = " + distance + ", scale = " + scale);
        view.setTranslationX(translationX);
    }

    public void onMotionUp() {
        for (int i = 0; isOpened && i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isPressed()) {
                view.performClick();
            }
        }
    }
}
