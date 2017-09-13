package com.shrw.duke.prison_roll_call.listener;

import android.content.Context;

/**
 * Created by rw-duke on 2017/9/13.
 */

public interface OnActivityOrFragmentArgListener<T> {
    void onData(Context context, T data);
}
