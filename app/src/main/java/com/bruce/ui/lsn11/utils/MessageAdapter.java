package com.bruce.ui.lsn11.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce.ui.R;

import java.util.Collections;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> implements ItemTouchHelperAdapterCallback {

    private List<Message> mList;
    private StartDragListener mStartDragListener;

    public MessageAdapter(List<Message> list, StartDragListener startDragListener) {
        mList = list;
        mStartDragListener = startDragListener;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MessageHolder holder, int position) {
        Message message = mList.get(position);
        holder.iv_logo.setImageResource(message.getLogo());
        holder.tv_name.setText(message.getName());
        holder.tv_lastMsg.setText(message.getLastMsg());
        holder.tv_time.setText(message.getTime());

        holder.iv_logo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mStartDragListener != null) {
                        mStartDragListener.onStartDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public boolean onItemSwiped(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        return false;
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        ImageView iv_logo;
        TextView tv_name;
        TextView tv_time;
        TextView tv_lastMsg;

        MessageHolder(View itemView) {
            super(itemView);
            iv_logo = itemView.findViewById(R.id.iv_logo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_lastMsg = itemView.findViewById(R.id.tv_lastMsg);
        }
    }
}
