package com.bruce.ui.lsn3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RadialGradientView extends View {
    private Paint mPaint;
    int cx = 500, cy = 500;
    private RadialGradient mRadialGradient;
    private Matrix mMatrix;

    public RadialGradientView(Context context) {
        super(context);
        init();
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mMatrix = new Matrix();
        mRadialGradient = new RadialGradient(cx, cy, 100, new int[]{Color.YELLOW, Color.GREEN, Color.TRANSPARENT, Color.RED}, null, Shader.TileMode.REPEAT);
        mRadialGradient.setLocalMatrix(mMatrix);
        mPaint.setShader(mRadialGradient);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(1000, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(500, 500, 500, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        mMatrix.setTranslate(x - cx, y - cy);
        mPaint.getShader().setLocalMatrix(mMatrix);
        invalidate();
        return true;
    }
}
