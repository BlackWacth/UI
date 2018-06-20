package com.bruce.ui.lsn3;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;

public class Lsn3HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn3_home);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_linear_gradient:
                startActivity(LinearGradientActivity.class);
                break;

            case R.id.btn_sweep_gradient:
                startActivity(SweepGradientActivity.class);
                break;

            case R.id.btn_radial_gradient:
                startActivity(RadialGradientActivity.class);
                break;

            case R.id.btn_bitmap_shader:
                startActivity(BitmapShaderActivity.class);
                break;

            case R.id.btn_compose_shader:
                startActivity(ComposeShaderActivity.class);
                break;
        }
    }
}
