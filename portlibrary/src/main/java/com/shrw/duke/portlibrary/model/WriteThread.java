package com.shrw.duke.portlibrary.model;

import android.text.TextUtils;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.portlibrary.common.utils.Parse;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by rw-duke on 2017/7/3.
 */

public class WriteThread extends Thread {
    private OutputStream outputStream;
    private String cmd = "";

    public WriteThread(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    @Override
    public void run() {
        super.run();

        try {
            if (!TextUtils.isEmpty(cmd) && outputStream != null) {
//                byte[] buffer = new byte[Constant.TYPE_TOUCH_INTERACTION_START];

                outputStream.write(Parse.hexString2Bytes(cmd));
                outputStream.write('\n');

            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            throw  new IllegalArgumentException();
        }

    }

    /**
     * 开始发送
     * @param cmd   指令
     */
    public void startWrite(String cmd){
        if (!TextUtils.isEmpty(cmd)){
            this.cmd = cmd;
            start();
        }else {
            throw new NullPointerException("命令不能为空");
        }

    }


}
