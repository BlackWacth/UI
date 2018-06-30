package com.bruce.ui.lsn6;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;

public class RevealDrawable extends Drawable {

    private Drawable mUnSelectedDrawable;
    private Drawable mSelectedDrawable;
    private Rect mOutRect;

    public RevealDrawable(Drawable unSelectedDrawable, Drawable selectedDrawable) {
        mUnSelectedDrawable = unSelectedDrawable;
        mSelectedDrawable = selectedDrawable;
        mOutRect = new Rect();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int tempWidth = (int) (getIntrinsicWidth() / 10000f * getLevel());

        canvas.save();
        Gravity.apply(Gravity.START, tempWidth, getIntrinsicHeight(), getBounds(), mOutRect);
        canvas.clipRect(mOutRect);
        mUnSelectedDrawable.draw(canvas);
        canvas.restore();

        canvas.save();
        Gravity.apply(Gravity.END, getIntrinsicWidth() - tempWidth, getIntrinsicHeight(), getBounds(), mOutRect);
        canvas.clipRect(mOutRect);
        mSelectedDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        Log.i("hzw", "bounds = " + bounds.toString());
        mUnSelectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return Math.max(mUnSelectedDrawable.getIntrinsicWidth(), mSelectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        return Math.max(mUnSelectedDrawable.getIntrinsicHeight(), mSelectedDrawable.getIntrinsicHeight());
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity() {
        return 0;
    }
}
