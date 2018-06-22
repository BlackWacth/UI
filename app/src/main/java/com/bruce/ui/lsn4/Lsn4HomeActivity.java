package com.bruce.ui.lsn4;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;
import com.bruce.ui.lsn4.filter.ColorFilterActivity;

public class Lsn4HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn4_home);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_color_filter:
                startActivity(ColorFilterActivity.class);
                break;
        }
    }
}
