package com.bruce.ui;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.lsn3.Lsn3HomeActivity;
import com.bruce.ui.lsn4.Lsn4HomeActivity;
import com.bruce.ui.lsn6.RevealEffectActivity;
import com.bruce.ui.lsn7.Lsn7HomeActivity;

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

            case R.id.btn_lsn4:
                startActivity(Lsn4HomeActivity.class);
                break;

            case R.id.btn_lsn6:
                startActivity(RevealEffectActivity.class);
                break;

            case R.id.btn_lsn7:
                startActivity(Lsn7HomeActivity.class);
                break;
        }
    }
}
