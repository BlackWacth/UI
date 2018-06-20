package com.bruce.ui.lsn3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bruce.ui.R;

public class ComposeShaderView extends View {

    private Paint mPaint00, mPaint01;

    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;


    public ComposeShaderView(Context context) {
        super(context);
        init();
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint00 = new Paint();
        mPaint01 = new Paint();
        mPaint01.setStyle(Paint.Style.FILL_AND_STROKE);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();

        Log.i("hzw", "mWidth = " + mWidth + ", mHeight = " + mHeight);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        LinearGradient linearGradient00 = new LinearGradient(0, 0, mWidth, mHeight, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint00.setShader(new ComposeShader(bitmapShader, linearGradient00, PorterDuff.Mode.MULTIPLY));

        LinearGradient linearGradient01 = new LinearGradient(0, mHeight, mWidth, mHeight * 2, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP);
//        mPaint01.setShader(new ComposeShader(linearGradient01, bitmapShader, PorterDuff.Mode.MULTIPLY));
//        mPaint01.setShader(bitmapShader);
        mPaint01.setShader(linearGradient01);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint00);

        canvas.drawRect(0, mHeight, mWidth, mHeight * 2, mPaint01);
        canvas.drawCircle(mWidth / 2, mHeight * 3 / 2, mWidth / 2, mPaint01);
    }
}


