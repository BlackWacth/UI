package com.bruce.ui.lsn20.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.bruce.ui.R;

public class ExtensibleListView extends ListView {

    private ImageView mImageView;
    private int mImageViewHeight;

    public ExtensibleListView(Context context) {
        super(context);
        init(context);
    }

    public ExtensibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExtensibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            ResetAnimation resetAnimation = new ResetAnimation(mImageViewHeight);
            resetAnimation.setInterpolator(new OvershootInterpolator());
            resetAnimation.setDuration(700);
            mImageView.startAnimation(resetAnimation);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

//        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
//        mImageView.requestLayout();
//        ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
//        layoutParams.height = mImageView.getHeight() - deltaY;
//        mImageView.setLayoutParams(layoutParams);

        changeHeight(mImageView.getHeight() - deltaY);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View header = (View) mImageView.getParent();
        int deltaY = header.getTop();
        Log.i("hzw", "deltaY = " + deltaY);
        if (mImageView.getHeight() > mImageViewHeight) {
//            mImageView.getLayoutParams().height = mImageView.getHeight() + deltaY;
//            mImageView.requestLayout();
            header.layout(header.getLeft(), 0, header.getRight(), header.getHeight());
            changeHeight(mImageView.getHeight() + deltaY);
        }
    }

    private void changeHeight(int height) {
//        mImageView.getLayoutParams().height = height;
//        mImageView.requestLayout();

        ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
        layoutParams.height = height;
        mImageView.setLayoutParams(layoutParams);
    }

    public void setZoomImageView(ImageView imageView) {
        mImageView = imageView;
    }

    class ResetAnimation extends Animation {
        private int mExtraHeight;
        private int mCurrentHeight;

        public ResetAnimation(int targetHeight) {
            mCurrentHeight = mImageView.getHeight();
            mExtraHeight = mImageView.getHeight() - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            mImageView.getLayoutParams().height = (int) (mCurrentHeight - mExtraHeight * interpolatedTime);
//            mImageView.requestLayout();
            changeHeight((int) (mCurrentHeight - mExtraHeight * interpolatedTime));
        }
    }
}
