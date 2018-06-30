package com.bruce.ui.lsn7;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;
import com.bruce.ui.lsn4.filter.ColorFilterActivity;
import com.bruce.ui.lsn4.xermode.XfermodeActivity;
import com.bruce.ui.lsn7.dragBubble.DragBubbleActivity;
import com.bruce.ui.lsn7.drawBezier.DrawBezierActivity;

public class Lsn7HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn7_home);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_drag_bubble:
                startActivity(DragBubbleActivity.class);
                break;

            case R.id.btn_draw_bezier:
                startActivity(DrawBezierActivity.class);
                break;
        }
    }
}
