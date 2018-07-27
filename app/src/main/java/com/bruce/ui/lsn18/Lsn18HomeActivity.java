package com.bruce.ui.lsn18;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.ui.R;
import com.bruce.ui.lsn18.widgets.MenuDrawerLayout;

public class Lsn18HomeActivity extends AppCompatActivity {

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
