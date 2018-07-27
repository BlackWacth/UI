package com.bruce.ui.lsn18.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MenuDrawerLayout extends DrawerLayout implements DrawerLayout.DrawerListener{

    private SlidMenuLayout mSlidMenuLayout;
    private MenuContentLayout mMenuContentLayout;
    private View mContentView;
    private float mSlideOffset;
    private float mTouchY;

    public MenuDrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public MenuDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof MenuContentLayout) {
                mMenuContentLayout = (MenuContentLayout) view;
            } else {
                mContentView = view;
            }
        }

        removeView(mMenuContentLayout);
        mSlidMenuLayout = new SlidMenuLayout(mMenuContentLayout);
        addView(mSlidMenuLayout);

        addDrawerListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mTouchY = ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            closeDrawers();
            mMenuContentLayout.onMotionUp();
            return super.dispatchTouchEvent(ev);
        }

        if (mSlideOffset < 0.8) {
            return super.dispatchTouchEvent(ev);
        } else {
            mSlidMenuLayout.setTouchY(mTouchY, mSlideOffset);
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        Log.i("hzw", "onDrawerSlide >> slideOffset = " + slideOffset);
        mSlideOffset = slideOffset;
        mSlidMenuLayout.setTouchY(mTouchY, slideOffset);
        mContentView.setTranslationX(drawerView.getWidth() * slideOffset / 3);
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        Log.i("hzw", "onDrawerOpened");
                setDrawerLockMode(LOCK_MODE_LOCKED_OPEN, GravityCompat.END);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        Log.i("hzw", "onDrawerClosed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        Log.i("hzw", "onDrawerStateChanged");
    }
}
