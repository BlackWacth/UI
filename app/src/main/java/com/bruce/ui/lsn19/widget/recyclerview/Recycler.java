package com.bruce.ui.lsn19.widget.recyclerview;

import android.util.SparseArray;
import android.view.View;

import java.util.Stack;

public class Recycler {

    private SparseArray<Stack<View>> mStackSparseArray;

    public Recycler(int typeNumber) {
        mStackSparseArray = new SparseArray<>(typeNumber);
    }

    public void addRecycledVeiw(int type, View view) {
        if (mStackSparseArray.get(type) == null) {
            Stack<View> stack = new Stack<>();
            stack.push(view);
            mStackSparseArray.put(type, stack);
        } else {
            mStackSparseArray.get(type).push(view);
        }
    }

    public View getRecycledView(int type) {
        try {
            if (mStackSparseArray.get(type) == null) {
                return null;
            } else {
                return mStackSparseArray.get(type).pop();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
