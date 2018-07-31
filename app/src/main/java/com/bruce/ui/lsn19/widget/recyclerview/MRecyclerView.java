package com.bruce.ui.lsn19.widget.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import com.bruce.ui.R;

import java.util.ArrayList;
import java.util.List;

public class MRecyclerView extends ViewGroup {

    private static final String TAG = "MRecyclerView";

    private Adapter mAdapter;
    private Recycler mRecycler;

    private int mScrollY;

    private List<View> mViewList;

    private int mCurrentY;
    private int mRowCount;
    private boolean isNeedReLayout;
    private int mWidth;
    private int mHeight;
    private int[] mHeights;

    private int mFirstRow;
    private int mTouchSlop;

    private Flinger mFlinger;
    private VelocityTracker mVelocityTracker;
    private int mMinVelocity;
    private int mMaxVelocity;

    public MRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public MRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setClickable(true);
        mViewList = new ArrayList<>();

        isNeedReLayout = true;

        mFlinger = new Flinger(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay().getMetrics(dm);
        Log.i(TAG, "height = " + dm.heightPixels);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure");
        if (mAdapter != null) {
            mRowCount = mAdapter.getCount();
            if (mHeights == null) {
                mHeights = new int[mRowCount];
            }
            for (int i = 0; i < mHeights.length; i++) {
                mHeights[i] = mAdapter.getHeight(i);
            }
        }

        int tempH = sumArray(mHeights, 0, mHeights.length);
        int h = Math.min(height, tempH);
        Log.i(TAG, "height = h > " + h);
        setMeasuredDimension(width, h);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout");
        if (isNeedReLayout || changed) {
            isNeedReLayout = false;
            mViewList.clear();
            removeAllViews();
            if (mAdapter != null) {
                mWidth = r - l;
                mHeight = b - t;

                int left, top, right, bottom;
                left = 0;
                right = mWidth;
                top = -mScrollY;
                for (int i = 0; i < mRowCount && top <= mHeight - mHeights[i]; i++) {
                    bottom = top + mHeights[i];
                    Log.i(TAG, "i = " + i);
                    mViewList.add(makeAndStep(i, left, top, right, bottom));
                    top = bottom;
                }

//                for (int i = 0; i < mRowCount && top <= mHeight; i++) {
//                    bottom = top + mHeights[i];
//                    Log.i(TAG, "i = " + i);
//                    mViewList.add(makeAndStep(i, left, top, right, bottom));
//                    top = bottom;
//                }
            }
        }
    }

    private View makeAndStep(int position, int left, int top, int right, int bottom) {
        View view = obtainView(position, right - left, bottom - top);
        if (view != null) {
            Log.i(TAG, "makeAndStep => position = " + position);
            view.layout(left, top, right, bottom);
            return view;
        }
        return null;
    }

    private View obtainView(int position, int width, int height) {
        if (mAdapter != null) {
            int type = mAdapter.getItemViewType(position);
            View contentView = null;
            if (mRecycler != null) {
                contentView = mRecycler.getRecycledView(type);
            }
            Log.i(TAG, "obtainView = position : " + position);
            View view = mAdapter.getView(position, contentView, this);
            if (view == null) {
                throw new RuntimeException("getView 不能为空");
            }

            view.setTag(R.id.tag_type_view, type);
            view.setTag(R.id.tag_row, position);

            view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            addView(view);
            return view;
        }
        return null;
    }

    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mCurrentY - (int) ev.getRawY()) > mTouchSlop) {
                    intercept = true;
                    Log.i(TAG, "onInterceptTouchEvent >> ACTION_MOVE : " + intercept);
                }
                break;
        }
        return intercept;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mFlinger.isFinished()) {
                    mFlinger.forceFinished();
                }
                mCurrentY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int lastY = (int) event.getRawY();
                int dy = mCurrentY - lastY;
                scrollBy(0, dy);
                mCurrentY = lastY;
                break;

            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                int vx = (int) velocityTracker.getXVelocity();
                int vy = (int) velocityTracker.getYVelocity();

                int initY = mScrollY + sumArray(mHeights, 0, mFirstRow);
                int maxY = Math.max(0, sumArray(mHeights, 0, mHeights.length) - mHeight);

                if (Math.abs(vx) > mMinVelocity || Math.abs(vy) > mMinVelocity) {
                    mFlinger.start(0, initY, 0, vy, 0, maxY);
                } else {
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        mScrollY += y;
        mScrollY = scrollBounds(mScrollY, mFirstRow, mHeights, mHeight);
        if (mScrollY > 0) { //向上滑
            while (mHeights[mFirstRow] <= mScrollY) {
                if (!mViewList.isEmpty()) {
                    removeTop();
                }
                mScrollY -= mHeights[mFirstRow];
                mFirstRow++;
            }
            while (getFilledHeight() <= mHeight) {
                addBottom();
            }
        } else if (mScrollY < 0) { //向下滑
            while (!mViewList.isEmpty() && getFilledHeight() - mHeights[mFirstRow + mViewList.size() - 1] >= mHeight) {
                removeBottom();
            }
            while (0 > mScrollY) {
                addTop();
                mFirstRow--;
                mScrollY += mHeights[mFirstRow + 1];
            }
        }
        repositionViews();
        awakenScrollBars();
    }

    private void repositionViews() {
        int top, bottom;
        top = -mScrollY;
        for (int i = 0; i < mViewList.size(); i++) {
            bottom = top + mHeights[mFirstRow + i];
            mViewList.get(i).layout(0, top, mWidth, bottom);
            TextView textView = (TextView) mViewList.get(i);
            Log.i(TAG, "repositionViews => text = " + textView.getText() + " top = " + textView.getTop() + ", size = " + mViewList.size() + " --  i = " + i);
            top = bottom;
        }
    }

    private void addTop() {
        addTopAndBottom(mFirstRow - 1, 0);
    }

    private void removeBottom() {
        removeView(mViewList.remove(mViewList.size() - 1));
    }

    private int getFilledHeight() {
        return sumArray(mHeights, mFirstRow, mViewList.size()) - mScrollY;
    }

    private void addBottom() {
        int size = mViewList.size() - 1;
        addTopAndBottom(mFirstRow + size + 1, size);
    }

    private void addTopAndBottom(int addRow, int index) {
        if (addRow < 0 || addRow >= mRowCount || index < 0) {
            return;
        }
        Log.i(TAG, "addTopAndBottom => addRow = " + addRow + ", index = " + index + ", mFirstRow = " + mFirstRow);
        mViewList.add(index, obtainView(addRow, mWidth, mHeights[addRow]));
    }

    private void removeTop() {
        removeView(mViewList.remove(0));
    }

    private int scrollBounds(int scrollY, int firstRow, int[] heights, int height) {
        if (scrollY > 0) { //向上滑
            scrollY = Math.min(scrollY, sumArray(mHeights, firstRow, heights.length - 1 - firstRow) - height);
        } else if (scrollY < 0) { //向下滑
            scrollY = Math.max(scrollY, -sumArray(heights, 0, firstRow));
        }
        return scrollY;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        int type = (int) view.getTag(R.id.tag_type_view);
        if (mRecycler != null) {
            mRecycler.addRecycledVeiw(type, view);
        }
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            mRecycler = new Recycler(mAdapter.getViewTypeCount());
        }
        mScrollY = 0;
        mFirstRow = 0;
        isNeedReLayout = true;
        requestLayout();
    }

    private class Flinger implements Runnable {

        private final Scroller mScroller;
        private int mLastX = 0;
        private int mLastY = 0;

        public Flinger(Context context) {
            mScroller = new Scroller(context);
        }

        void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY) {
            mScroller.fling(initX, initY, initialVelocityX, initialVelocityY, 0, maxX, 0, maxY);
            mLastX = initX;
            mLastY = initY;

            post(this);
        }

        boolean isFinished() {
            return mScroller.isFinished();
        }

        void forceFinished() {
            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
        }

        @Override
        public void run() {
            if (isFinished()) {
                return;
            }

            boolean more = mScroller.computeScrollOffset();
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            int dX = mLastX - currX;
            int dY = mLastY - currY;

            if (dX != 0 || dY != 0) {
                scrollBy(dX, dY);
                mLastX = currX;
                mLastY = currY;
            }

            if (more) {
                post(this);
            }
        }
    }
}
