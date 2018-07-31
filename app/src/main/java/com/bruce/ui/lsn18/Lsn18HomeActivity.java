package com.bruce.ui.lsn18;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;

public class Lsn18HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn18_home);
    }

    public void test(View view) {
        TextView tv = (TextView) view;
        Toast.makeText(this, "调用" + tv.getText() + "菜单...", Toast.LENGTH_SHORT).show();
    }
}
