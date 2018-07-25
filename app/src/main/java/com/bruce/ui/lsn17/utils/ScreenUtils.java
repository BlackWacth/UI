package com.bruce.ui.lsn17.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {

    /**
     * 获取底部虚拟按键的高度，通过反射的方式
     *
     * @param context context
     * @return 虚拟按键高度
     */
    public static int getNavigationHeight(Context context) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object object = cls.newInstance();
            String heightStr = cls.getField("navigation_bar_height").get(object).toString();
            return context.getApplicationContext().getResources().getDimensionPixelSize(Integer.parseInt(heightStr));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取底部虚拟按键的高度，通过真实分辨率 减去 内容分辨率
     * @param context context
     * @return 虚拟按键高度
     */
    public static int getNavigationHeightByWindow(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return -1;
        }
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int realHeight = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
        display.getMetrics(displayMetrics);
        int height = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
        return realHeight - height;
    }


    /**
     * 获取顶部状态栏的高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context context) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object object = cls.newInstance();
            String heightStr = cls.getField("status_bar_height").get(object).toString();
            return context.getApplicationContext().getResources().getDimensionPixelSize(Integer.parseInt(heightStr));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
