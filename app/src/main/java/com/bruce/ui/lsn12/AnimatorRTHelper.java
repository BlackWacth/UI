package com.bruce.ui.lsn12;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class AnimatorRTHelper {

    private final static String TAG = "AnimatorRTHelper";

    /**
     * 创建ViewPropertyAnimatorRT, ViewPropertyAnimatorRT为RenderThread绑定属性动画的载体
     * @param view view
     * @return ViewPropertyAnimatorRT对象
     */
    private static Object createViewPropertyAnimatorRT(View view) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> cls = Class.forName("android.view.ViewPropertyAnimatorRT");
            Constructor<?> constructor = cls.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            return constructor.newInstance(view);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            Log.e(TAG, "createViewPropertyAnimatorRT --- 创建ViewPropertyAnimatorRT对象失败 -- " + e.toString());
        }
        return null;
    }

    /**
     * 设置ViewPropertyAnimator中mRTBackend属性为ViewPropertyAnimatorRT对象
     * 该对象为ViewPropertyAnimator#startAnimation()首要判断对象，也是异步动画的载体(ViewPropertyAnimatorRT)属性
     * @param animator ViewPropertyAnimator对象(可以通过view.animator()创建)
     * @param ort ViewPropertyAnimatorRT对象
     */
    private static void setViewPropertyAnimatorRT(ViewPropertyAnimator animator, Object ort) {
        try {
            Class<?> cls = Class.forName("android.view.ViewPropertyAnimator");
            Field mRTBackendField = cls.getDeclaredField("mRTBackend");
            mRTBackendField.setAccessible(true);
            mRTBackendField.set(animator, ort);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "setViewPropertyAnimatorRT --- 设置ViewPropertyAnimator中mRTBackend属性失败 -- " + e.toString());
        }
    }

    /**
     * 配置ViewPropertyAnimator动画，通过GPU异步执行。
     * @param animator ViewPropertyAnimator对象(可以通过view.animator()创建)
     * @param view view
     * @return true为设置成功
     */
    public static boolean setAnimatorByRT(ViewPropertyAnimator animator, View view) {
        try {
            Object ort = createViewPropertyAnimatorRT(view);
            if (ort != null) {
                setViewPropertyAnimatorRT(animator, ort);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
