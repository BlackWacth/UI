package com.bruce.ui.lsn16;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

public class ToolBarBehavior extends Behavior {

    private int mUnconsumedY;

    public ToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Log.i("hzw", "dxConsumed = " + dxConsumed + ", dyConsumed = " + dyConsumed + ", dxUnconsumed = " + dxUnconsumed + ", dyUnconsumed = " + dyUnconsumed + ", view.getScrollY() = " + view.getScrollY());

        if (dyConsumed <= 0 && dyUnconsumed < 0) {
            mUnconsumedY = mUnconsumedY + Math.abs(dyUnconsumed) >= view.getHeight() ? view.getHeight() : mUnconsumedY + Math.abs(dyUnconsumed);
            view.setAlpha(1f - mUnconsumedY * 1.0f / view.getHeight());
        } else if (dxUnconsumed <= 0 && dyConsumed > 0) {
            mUnconsumedY = mUnconsumedY - Math.abs(dyConsumed) <= 0 ? 0 : mUnconsumedY - Math.abs(dyConsumed);
            view.setAlpha(1f - mUnconsumedY * 1.0f / view.getHeight());
        }
    }
}
