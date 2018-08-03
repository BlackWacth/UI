package com.bruce.ui;

import android.os.Bundle;
import android.view.View;

import com.bruce.ui.lsn11.Lsn11HomeActivity;
import com.bruce.ui.lsn12.Lsn12HomeActivity;
import com.bruce.ui.lsn16.Lsn16HomeActivity;
import com.bruce.ui.lsn17.Lsn17HomeActivity;
import com.bruce.ui.lsn18.Lsn18HomeActivity;
import com.bruce.ui.lsn19.Lsn19HomeActivity;
import com.bruce.ui.lsn20.Lsn20HomeActivity;
import com.bruce.ui.lsn21.Lsn21HomeActivity;
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

            case R.id.btn_lsn11:
                startActivity(Lsn11HomeActivity.class);
                break;

            case R.id.btn_lsn12:
                startActivity(Lsn12HomeActivity.class);
                break;

            case R.id.btn_lsn16:
                startActivity(Lsn16HomeActivity.class);
                break;

            case R.id.btn_lsn17:
                startActivity(Lsn17HomeActivity.class);
                break;

            case R.id.btn_lsn18:
                startActivity(Lsn18HomeActivity.class);
                break;

            case R.id.btn_lsn19:
                startActivity(Lsn19HomeActivity.class);
                break;

            case R.id.btn_lsn20:
                startActivity(Lsn20HomeActivity.class);
                break;

            case R.id.btn_lsn21:
                startActivity(Lsn21HomeActivity.class);
                break;
        }
    }
}
