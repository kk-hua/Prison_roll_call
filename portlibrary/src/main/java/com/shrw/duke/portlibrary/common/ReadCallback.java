package com.shrw.duke.portlibrary.common;

/**
 * Created by rw-duke on 2017/5/25.
 */

public interface ReadCallback {

    void exec(byte[] buffer, int size);
}
