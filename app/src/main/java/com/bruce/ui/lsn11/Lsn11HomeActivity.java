package com.bruce.ui.lsn11;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;
import com.bruce.ui.lsn11.utils.DataUtils;
import com.bruce.ui.lsn11.utils.Message;
import com.bruce.ui.lsn11.utils.MessageAdapter;
import com.bruce.ui.lsn11.utils.MessageItemTouchCallback;
import com.bruce.ui.lsn11.utils.StartDragListener;

import java.util.List;

public class Lsn11HomeActivity extends BaseActivity implements StartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn11_home);
        RecyclerView recyclerView = findViewById(R.id.rv_recycler_view);

        List<Message> list = DataUtils.init();
        MessageAdapter adapter = new MessageAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        MessageItemTouchCallback callback = new MessageItemTouchCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
