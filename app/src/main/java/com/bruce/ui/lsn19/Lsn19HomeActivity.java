package com.bruce.ui.lsn19;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;
import com.bruce.ui.lsn19.widget.recyclerview.Adapter;
import com.bruce.ui.lsn19.widget.recyclerview.MRecyclerView;

public class Lsn19HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn19_home);

        MRecyclerView recyclerView = findViewById(R.id.mrv_recycler);
        recyclerView.setAdapter(new MyAdapter(this));
    }

    class MyAdapter implements Adapter {
        LayoutInflater inflater;
        private int height;

        public MyAdapter(Context context) {
            Resources resources = context.getResources();
            height = resources.getDimensionPixelSize(R.dimen.table_height);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 50;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_table1, parent, false);
            }
            //
            TextView textView = convertView.findViewById(R.id.text1);
            textView.setText("第 " + position + "行");

            return convertView;
        }

        @Override
        public int getItemViewType(int row) {
            return 0;
        }

        @Override
        public int getHeight(int index) {
            return height;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }
    }
}
