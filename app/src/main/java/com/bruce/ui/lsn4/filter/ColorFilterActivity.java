package com.bruce.ui.lsn4.filter;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;

public class ColorFilterActivity extends BaseActivity {

    private FilterView mFilterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_filter);
        mFilterView = findViewById(R.id.fv_filter_view);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_negative_effect:
                mFilterView.negativeEffect();
                break;

            case R.id.btn_color_enhanced:
                mFilterView.colorEnhanced();
                break;

            case R.id.btn_black_white_photo:
                mFilterView.blackWhitePhoto();
                break;

            case R.id.btn_retro_effect:
                mFilterView.retroEffect();
                break;
        }
    }

}
