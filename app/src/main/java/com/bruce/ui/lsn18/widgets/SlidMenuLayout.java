package com.bruce.ui.lsn18.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SlidMenuLayout extends RelativeLayout{

    private MenuContentLayout mMenuContentLayout;
    private MenuBackgroundView mMenuBackgroundView;

    public SlidMenuLayout(MenuContentLayout menuContentLayout) {
        this(menuContentLayout.getContext());
        mMenuContentLayout = menuContentLayout;
        init();
    }

    public SlidMenuLayout(Context context) {
        this(context, null);
    }

    public SlidMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mMenuContentLayout == null) {
            return;
        }
        Log.i("hzw","--------------");

        setLayoutParams(mMenuContentLayout.getLayoutParams());
        mMenuBackgroundView = new MenuBackgroundView(getContext());
        mMenuBackgroundView.setColor(mMenuContentLayout.getBackground());
        mMenuContentLayout.setBackgroundColor(Color.TRANSPARENT);

        addView(mMenuBackgroundView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mMenuContentLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setTouchY(float y, float slideOffset) {
        mMenuBackgroundView.setTouchY(y, slideOffset);
        mMenuContentLayout.setTouchY(y, slideOffset);
    }
}
