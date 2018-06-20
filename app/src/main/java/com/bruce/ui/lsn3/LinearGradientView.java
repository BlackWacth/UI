package com.bruce.ui.lsn3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class LinearGradientView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Matrix mMatrix;

    private float mSpeed = 20;
    private float mTextWidth;
    private float mCurrentX = 0;
    private float mGradientSize;

    public LinearGradientView(Context context) {
        super(context);
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        String text = getText().toString();
        mTextWidth = getPaint().measureText(text);
        mGradientSize = mTextWidth / text.length() * 4f;
        mLinearGradient = new LinearGradient(0, 0, mGradientSize, 0, new int[]{0x22ffffff, 0xffffffff, 0x22ffffff}, null, Shader.TileMode.CLAMP);
        getPaint().setShader(mLinearGradient);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCurrentX += mSpeed;
        if (mCurrentX > getMeasuredWidth() - mGradientSize || mCurrentX < 1) {
            mSpeed = -mSpeed;
        }
        mMatrix.setTranslate(mCurrentX, 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(50);
    }
}
