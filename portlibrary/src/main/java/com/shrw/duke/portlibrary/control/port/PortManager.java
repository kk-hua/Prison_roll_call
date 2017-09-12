package com.shrw.duke.portlibrary.control.port;

import com.shrw.duke.portlibrary.common.ReadCallback;
import com.shrw.duke.portlibrary.model.ReadThread;
import com.shrw.duke.portlibrary.model.WriteThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.logging.Handler;

import serial.utils.SerialPort;

/**
 * Created by rw-duke on 2017/6/19.
 */

public class PortManager {
    private static PortManager manager = null;
    private static boolean isSearch = false;
    private PortUtils portUtils;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ReadThread readThread;
    private SerialPort port;
    private WriteThread writeThread;

    public PortManager() {
        this.portUtils = PortUtils.getInstance();
    }

    public static PortManager getInstance() {
        if (manager == null) {
            synchronized (PortManager.class) {
                if (null == manager) {
                    manager = new PortManager();
                }
            }
        }
        return manager;
    }


    public boolean isIsSearch() {
        return isSearch;
    }

    /**
     * 上电
     */
    public void powerUp() {
        if (portUtils == null) {
            throw new IllegalArgumentException("SerialPort is not initialized.");
        } else {
            portUtils.powerUp();
        }
    }

    /**
     * 断电
     */
    public void powerDown() {
        if (portUtils == null) {
            throw new IllegalArgumentException("SerialPort is not initialized.");
        } else {
            portUtils.powerDown();
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        portUtils.closeSerialPort();
        port = null;
    }

    /**
     * 打开串口
     *
     * @throws SecurityException
     * @throws IOException
     * @throws InvalidParameterException
     */

    public void openSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (portUtils == null) {
            throw new IllegalArgumentException("SerialPort is not initialized.");
        } else {
            port = portUtils.getSerialPort();
//            inputStream = port.getInputStream();
//            outputStream = port.getOutputStream();
//            readThread = new ReadThread(inputStream,readCallback);
//            readThread.startRead();
//            readThread.start();
//            isSearch = true;
        }
    }

    public void startRead(ReadCallback readCallback) {
        if (port == null) {
            throw new IllegalArgumentException("串口没有打开");
        } else {
            inputStream = port.getInputStream();
            readThread = new  ReadThread(inputStream, readCallback);
            readThread.startRead();
            readThread.start();
            isSearch = true;
        }
    }

    /**
     * 停止接收
     */
    public void stopRead() {
        try {
            //关闭输入流
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            //关闭读取线程
            if (readThread != null)
                readThread.stopRead();
            isSearch = false;
        } catch (IOException e) {
            isSearch = true;
            e.printStackTrace();
        }
    }

    /**
     * 发送指令
     *
     * @param cmd 指令
     * @throws NullPointerException 如果cmd为空
     */
    public void write(String cmd) throws NullPointerException {
        if (port == null) {
            throw new IllegalArgumentException("串口没有打开");
        } else {
            outputStream = port.getOutputStream();
            writeThread = new WriteThread(outputStream);
            writeThread.startWrite(cmd);
        }
    }

}
