package com.bruce.ui;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.lsn3.Lsn3HomeActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lsn3:
                startActivity(Lsn3HomeActivity.class);
                break;
        }
    }
}
