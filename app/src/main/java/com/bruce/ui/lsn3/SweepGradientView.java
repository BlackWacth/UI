package com.bruce.ui.lsn3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SweepGradientView extends View {

    private Paint mCirclePaint;
    private Paint mRadarPaint;
    private int mWidth, mHeight;
    private Matrix mMatrix;
    private int mScanSpeed = 10;


    //五个圆
    private float[] pots = {0.05f, 0.1f, 0.15f, 0.2f, 0.25f};

    public SweepGradientView(Context context) {
        super(context);
        init(context, null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(1);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAlpha(100);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.parseColor("#B0C4DE"));

        mRadarPaint = new Paint();
        mRadarPaint.setAntiAlias(true);
        mRadarPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mMatrix = new Matrix();

        post(mRunnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 取屏幕的宽高是为了把雷达放在屏幕的中间
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mWidth = mHeight = Math.min(mWidth, mHeight);
        Log.i("hzw", "mWidth = " + mWidth + ", mHeight = " + mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("hzw", "w = " + w + ", h = " + h);
        SweepGradient sweepGradient = new SweepGradient(mWidth / 2, mHeight / 2, new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
        mRadarPaint.setShader(sweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (float pot : pots) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * pot, mCirclePaint);
        }

        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * pots[4], mRadarPaint);

        canvas.restore();

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mMatrix.postRotate(mScanSpeed, mWidth / 2, mHeight / 2);
            invalidate();
            postDelayed(mRunnable, 50);
        }
    };
}
