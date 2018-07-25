package com.bruce.ui.lsn11.utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class MessageItemTouchCallback extends ItemTouchHelper.Callback {

    private final String TAG = "hzw";

    private ItemTouchHelperAdapterCallback mCallback;

    public MessageItemTouchCallback(ItemTouchHelperAdapterCallback callback) {
        mCallback = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "getMovementFlags");
        //拖动方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //滑动方向
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    //item 拖动时回调
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.i(TAG, "onMove");
        if (mCallback != null) {
            mCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    // item滑动时候回到
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i(TAG, "onSwiped");
        if (mCallback != null) {
            mCallback.onItemSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Log.i(TAG, "onChildDraw");
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "getSwipeThreshold");
        return 0.2f;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        Log.i(TAG, "getSwipeEscapeVelocity");
        return 5f;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        Log.i(TAG, "getSwipeVelocityThreshold");
        return super.getSwipeVelocityThreshold(defaultValue);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //true：item可以滑动，false：item不可以滑动
        //滑动首先回调的方法，如果为false，后续回调不执行，后续有 getMovementFlags， onChildDraw，getMovementFlags，getSwipeVelocityThreshold，getSwipeThreshold
        Log.i(TAG, "isItemViewSwipeEnabled");
        return true;
    }

    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "getMoveThreshold");
        return 1f;
    }

    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        Log.i(TAG, "getAnimationDuration");
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }
}
