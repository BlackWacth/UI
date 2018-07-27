package com.bruce.ui.lsn17;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

import com.bruce.ui.R;
import com.bruce.ui.lsn17.utils.WindowBarManagerUtils;
import com.bruce.ui.lsn17.widget.ColorPicker;

public class Lsn17HomeActivity extends AppCompatActivity {

    private ColorPicker mColorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn17_home);
        mColorPicker = findViewById(R.id.color_picker);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applySelectedColor();
            }
        });

//        WindowBarManagerUtils.transparencyBar(this);

        ViewPropertyAnimator viewPropertyAnimator = button.animate().scaleX(1);
        viewPropertyAnimator.start();
    }

    private void applySelectedColor() {
        int selected = mColorPicker.getColor();
        int color = Color.argb(153, Color.red(selected), Color.green(selected), Color.blue(selected));
        WindowBarManagerUtils.setStatusBarColor(this, color);
        WindowBarManagerUtils.setNavigationBarColor(this, color);
    }

}
