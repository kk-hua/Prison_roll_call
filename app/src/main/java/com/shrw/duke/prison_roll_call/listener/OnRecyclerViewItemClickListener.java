package com.shrw.duke.prison_roll_call.listener;

import android.view.View;

import com.shrw.duke.prison_roll_call.entity.FileInfo;

/**
 * Created by rw-duke on 2017/9/12.
 */

public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View view, int position, T data);
}
