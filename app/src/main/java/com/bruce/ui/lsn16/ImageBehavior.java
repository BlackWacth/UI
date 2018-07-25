package com.bruce.ui.lsn16;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ImageBehavior extends Behavior {

    private int mHeight;
    private int mUnconsumedY;

    public ImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeight = 400;
        mUnconsumedY = 0;
    }

    @Override
    public void onNestedScroll(@NonNull View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i("hzw", "dxConsumed = " + dxConsumed + ", dyConsumed = " + dyConsumed + ", dxUnconsumed = " + dxUnconsumed + ", dyUnconsumed = " + dyUnconsumed + ", view.getScrollY() = " + view.getScrollY());
        if (dyConsumed <= 0 && dyUnconsumed < 0) {
            mUnconsumedY = mUnconsumedY + Math.abs(dyUnconsumed) >= mHeight ? mHeight : mUnconsumedY + Math.abs(dyUnconsumed);
        } else if (dxUnconsumed <= 0 && dyConsumed > 0) {
            mUnconsumedY = mUnconsumedY - Math.abs(dyConsumed) <= 0 ? 0 : mUnconsumedY - Math.abs(dyConsumed);
        }
        Log.i("hzw", "mUnconsumedY = " + mUnconsumedY);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 150 + mUnconsumedY;
        view.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {

        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }
}
