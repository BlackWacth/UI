package com.bruce.ui.lsn16;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.bruce.ui.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BehaviorRelativeLayout extends RelativeLayout implements NestedScrollingParent {

    private static final String TAG = "hzw";
    private float mLastX;
    private float mLastY;
    private float mMoveX;
    private float mMoveY;

    public BehaviorRelativeLayout(Context context) {
        this(context, null);
    }

    public BehaviorRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BehaviorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getRawX();
                mMoveY = event.getRawY();
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    LayoutParams params = (LayoutParams) child.getLayoutParams();
                    if (params.getBehavior() != null) {

                    }
                }
                mLastX = mMoveX;
                mLastY = mMoveY;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate -- ");
    }

    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        Log.i(TAG, "generateLayoutParams -- ");
        //子控件LayoutParams包装
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                behavior.onNestedScrollAccepted(getChildAt(i), target, axes);
            }
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                behavior.onStopNestedScroll(getChildAt(i));
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                behavior.onNestedScroll(getChildAt(i), dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
            }
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                behavior.onNestedPreScroll(getChildAt(i), dx, dy, consumed);
            }
        }
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                return behavior.onNestedFling(getChildAt(i), velocityX, velocityY, consumed);
            }
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                return behavior.onNestedPreFling(getChildAt(i), velocityX, velocityY);
            }
        }
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        for (int i = 0; i < getChildCount(); i++) {
            Behavior behavior = ((LayoutParams) getChildAt(i).getLayoutParams()).getBehavior();
            if (behavior != null) {
                return behavior.getNestedScrollAxes();
            }
        }
        return 0;
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        private Behavior mBehavior;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.BehaviorRelativeLayout);
            mBehavior = parseBehavior(c, attrs, typedArray.getString(R.styleable.BehaviorRelativeLayout_behavior));
            Log.i("hzw", "LayoutParams : " + mBehavior);
            typedArray.recycle();
        }

        private Behavior parseBehavior(Context c, AttributeSet attrs, String name) {
            if (TextUtils.isEmpty(name)) {
                return null;
            }
            try {
                Class<?> cls = Class.forName(name, true, c.getClassLoader());
                Constructor<?> constructor = cls.getConstructor(Context.class, AttributeSet.class);
                constructor.setAccessible(true);
                return (Behavior) constructor.newInstance(c, attrs);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }


        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
        }

        public Behavior getBehavior() {
            return mBehavior;
        }
    }
}
