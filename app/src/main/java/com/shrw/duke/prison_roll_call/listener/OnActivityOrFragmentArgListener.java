package com.shrw.duke.prison_roll_call.listener;

import android.content.Context;

import com.shrw.duke.prison_roll_call.common.MessageType;

/**
 * Created by rw-duke on 2017/9/13.
 */

public interface OnActivityOrFragmentArgListener<T> {
    /**
     *
     * @param context 上下文
     * @param data  数据
     * @param type  消息类型
     */
    void onData(Context context, T data, int type);
}
