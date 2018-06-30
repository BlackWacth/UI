package com.bruce.ui.lsn7.dragBubble;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DragBubbleView extends View {

    private static final int DEFAULT_BUBBLE_RADIUS = 10; //气泡默认半径
    private static final int DEFAULT_BUBBLE_COLOR = Color.parseColor("#e65100"); //气泡默认颜色

    public DragBubbleView(Context context) {
        super(context);
        init(context, null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }


    /**
     * 气泡状态
     */
    enum BubbleState {
        DEFAULT, //静止
        CONNECT, //连接
        APART,   //分离
        DISMISS  //消失
    }
}
