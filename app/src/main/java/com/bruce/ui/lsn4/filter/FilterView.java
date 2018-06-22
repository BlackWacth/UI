package com.bruce.ui.lsn4.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bruce.ui.R;

public class FilterView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    public FilterView(Context context) {
        super(context);
        init();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xyjy2);
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    /**
     * 底片效果
     */
    public void negativeEffect() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                -1, 0, 0, 0, 255,
                0, -1, 0, 0, 255,
                0, 0, -1, 0, 255,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        invalidate();
    }

    /**
     * 颜色增强
     */
    public void colorEnhanced() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1.5f, 0, 0, 0, 0,
                0, 1.5f, 0, 0, 0,
                0, 0, 1.5f, 0, 0,
                0, 0, 0, 1.5f, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        invalidate();
    }

    /**
     * 黑白照片
     */
    public void blackWhitePhoto() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        invalidate();
    }

    /**
     * 复古效果
     */
    public void retroEffect() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1 / 2f, 1 / 2f, 1 / 2f, 0, 0,
                1 / 3f, 1 / 3f, 1 / 3f, 0, 0,
                1 / 4f, 1 / 4f, 1 / 4f, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        invalidate();
    }
}
