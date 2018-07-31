package com.bruce.ui.lsn19.widget.recyclerview;

import android.view.View;
import android.view.ViewGroup;

public interface Adapter {

    int getCount();

    int getItemViewType(int row);

    int getViewTypeCount();

    int getHeight(int index);

    View getView(int position, View contentView, ViewGroup parent);

}
