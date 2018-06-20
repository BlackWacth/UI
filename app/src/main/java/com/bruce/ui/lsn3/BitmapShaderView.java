package com.bruce.ui.lsn3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bruce.ui.R;

public class BitmapShaderView extends View {

    private final static int FACTOR = 2;
    private final static int RADIUS = 150;

    private Bitmap mBitmap;
    private Bitmap mBitmapScale;
    private ShapeDrawable mShapeDrawable;
    private Matrix mMatrix;

    public BitmapShaderView(Context context) {
        super(context);
        init();
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.biubiu);
        mBitmapScale = mBitmap;
        mBitmapScale = Bitmap.createScaledBitmap(mBitmapScale, mBitmapScale.getWidth() * FACTOR, mBitmapScale.getHeight() * FACTOR, true);
        BitmapShader bitmapShader = new BitmapShader(mBitmapScale, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        mShapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // 将放大的图片往相反的方向挪动
        mMatrix.setTranslate(RADIUS - x * FACTOR, RADIUS - y *FACTOR);
        mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);
        // 切出手势区域点位置的圆
        mShapeDrawable.setBounds(x-RADIUS,y - RADIUS, x + RADIUS, y + RADIUS);
        invalidate();
        return true;
    }
}
