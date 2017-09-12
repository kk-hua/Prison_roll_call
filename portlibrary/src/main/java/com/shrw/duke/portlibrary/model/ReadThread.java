package com.shrw.duke.portlibrary.model;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.util.Log;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.portlibrary.common.ReadCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadThread extends Thread {
    private static final String TAG = "ReadThread";
    public static boolean m_stop = true;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ReadCallback readCallback;

    public ReadThread(InputStream mInputStream){
        this.mInputStream = mInputStream;
    }

    public ReadThread(InputStream mInputStream, ReadCallback readCallback) {
//        this.mOutputStream = mOutputStream;
        this.mInputStream = mInputStream;
        this.readCallback = readCallback;
    }

    public void setReadCallback(ReadCallback readCallback){
        this.readCallback = readCallback;
    }

    public void run() {
        super.run();
        while (!m_stop) {
            try {

                byte[] buffer = new byte[Constant.TYPE_TOUCH_INTERACTION_START];
                if (this.mInputStream != null) {
                    int size = this.mInputStream.read(buffer);
                    if (size > 0) {
                        this.readCallback.exec(buffer, size);
                    }
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void startRead() {
        m_stop = false;
    }

    public void stopRead() {
        m_stop = true;
    }
}