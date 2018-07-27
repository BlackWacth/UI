package com.bruce.ui.lsn18.widgets;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 侧滑菜单的背景
 */
public class MenuBackgroundView extends View {

    private Paint mPaint;
    private Path mPath;

    private float mWidth;
    private float mBeginX;
    private float mBeginY;
    private float mEndX;
    private float mEndY;

    public MenuBackgroundView(Context context) {
        this(context, null);
    }

    public MenuBackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setColor(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        if (drawable instanceof ColorDrawable) {
            mPaint.setColor(((ColorDrawable)drawable).getColor());
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapShader shader = new BitmapShader(((BitmapDrawable)drawable).getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        float offset = h / 8;

        //开始点
        mBeginX = 0;
        mBeginY = -offset;
        //结束点
        mEndX = 0;
        mEndY = h + offset;
    }

    public void setTouchY(float y, float slideOffset) {
        Log.i("hzw", "MenuBackgroundView --> setTouchY >> y = " + y + ", percent = " + slideOffset);
        if (mPath == null) {
            mPath = new Path();
        }
        mPath.reset();

        mPath.moveTo(mBeginX, mBeginY);
        mPath.quadTo(mWidth * slideOffset * 2f, y, mEndX, mEndY);
        mPath.close();

        invalidate();
    }
}
