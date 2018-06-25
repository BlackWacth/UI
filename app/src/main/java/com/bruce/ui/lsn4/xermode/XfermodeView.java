package com.bruce.ui.lsn4.xermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class XfermodeView extends View {

    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };

    private Paint mPaint;
    private float mItemSize = 0;
    private float mCircleRadius = 0;
    private float mRectSize = 0;
    private int mCircleColor = 0xffffcc44;
    private int mRectColor = 0xff66aaff;

    private int mRow = 0;
    private int mColumn = 4;
    private int mSpace = 30;
    private int mPadding = 20;
    private int mWidth = 0;
    private float mItemX;
    private float mItemY;

    public XfermodeView(Context context) {
        super(context);
        init();
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(25);
        mPaint.setStrokeWidth(2);

        mRow = sModes.length % mColumn != 0 ? sModes.length / mColumn + 1 : sModes.length / mColumn;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mItemSize = (mWidth - mSpace * (mColumn + 1)) / mColumn;
        mItemX = mSpace;
        mItemY = mSpace;
        mCircleRadius = (mItemSize - mPadding * 2) / 3f;
        mRectSize = (mItemSize - mPadding * 2) * 2f / 3f;
        int height = (int) (mRow * (mItemSize + mSpace) + mSpace);

        setMeasuredDimension(mWidth, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#a5d6a7"));

//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//        for (int row = 0; row < mRow; row++) {
//            for (int column = 0; column < mColumn; column++) {
//                int layer = canvas.saveLayer(0, 0, width, height, null);
//                mPaint.setXfermode(null);
//                int index = row * mColumn + column;
//                float translateX = (mItemSize + mSpace) * column + mSpace;
//                float translateY = (mItemSize + mSpace) * row + mSpace;
//                canvas.translate(translateX, translateY);
//
//                //绘制大边框
//                mPaint.setColor(Color.BLACK);
//                mPaint.setStyle(Paint.Style.STROKE);
//                canvas.drawRect(0, 0, mItemSize, mItemSize, mPaint);
//
//                //绘制文字
//                String text = index + " " + sLabels[index];
//                mPaint.setColor(Color.BLACK);
//                mPaint.setStyle(Paint.Style.FILL);
//                float textXOffset = (mItemSize - mPaint.measureText(text)) / 2f;
//                float textYOffset = 30;
//                canvas.drawText(text, textXOffset, textYOffset, mPaint);
//                canvas.translate(mPadding, mItemSize - mCircleRadius * 3);
//
//                //绘制文字
//                mPaint.setStyle(Paint.Style.FILL);
//                mPaint.setColor(mCircleColor);
//                float left = mCircleRadius;
//                float top = mCircleRadius;
//                canvas.drawCircle(left, top, mCircleRadius, mPaint);
//
//                //绘制文字
//                mPaint.setXfermode(sModes[index]);
//                mPaint.setColor(mRectColor);
//                float right = mCircleRadius + mRectSize;
//                float bottom = mCircleRadius + mRectSize;
//                canvas.drawRect(left, top, right, bottom, mPaint);
//                canvas.restoreToCount(layer);
//            }
//        }

        for (int row = 0; row < mRow; row++) {
            mItemY = mSpace + row * (mItemSize + mSpace);
            for (int column = 0; column < mColumn; column++) {
                mItemX = mSpace + column * (mItemSize + mSpace);
                int index = row * mColumn + column;
                if (index >= sModes.length) {
                    break;
                }
                mPaint.setXfermode(null);

                //绘制大边框
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.BLACK);
                canvas.drawRect(mItemX, mItemY, mItemX + mItemSize, mItemY + mItemSize, mPaint);


                //绘制文字
                mPaint.setStyle(Paint.Style.FILL);
                String text = index + " " + sLabels[index];
                float textX = mItemX + (mItemSize - mPaint.measureText(text)) / 2f;
                canvas.drawText(text, textX, mItemY + 30, mPaint);

                //绘制圆，为源，黄色
                float cx = mItemX + mPadding + mCircleRadius;
                float cy = mItemY + mItemSize - mRectSize;
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mCircleColor);
                mPaint.setXfermode(null);
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);

                //绘制目标，为目标，蓝色
                mPaint.setColor(mRectColor);
                mPaint.setXfermode(sModes[index]);
                canvas.drawRect(cx, cy, cx + mRectSize, cy + mRectSize, mPaint);
            }
        }
    }
}
