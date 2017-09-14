package com.shrw.duke.prison_roll_call.listener;

import android.view.View;

/**
 * Created by rw-duke on 2017/9/14.
 */

public interface OnRecyclerViewItemLongClickListener<T> {
    void onItemLongClick(View view, int position, T data);
}
