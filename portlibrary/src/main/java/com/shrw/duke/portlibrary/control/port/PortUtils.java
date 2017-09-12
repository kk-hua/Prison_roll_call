package com.shrw.duke.portlibrary.control.port;

import android.util.Log;


import com.shrw.duke.portlibrary.common.Constant;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import serial.utils.SerialPort;

/**
 * Created by rw-duke on 2017/6/19.
 */

public class PortUtils {
    private static PortUtils portUtils=null;
    private SerialPort mSerialPort;

    private PortUtils(){ }

    public static PortUtils getInstance(){
        if (portUtils==null){
            synchronized (PortUtils.class) {
                if (null == portUtils) {
                    portUtils = new PortUtils();
                }
            }
        }
        return portUtils;
    }


    /**
     *上电
     */
    public void powerUp(){
        SerialPort.SetIoState(Constant.IOCTRL_PMU_BARCODE_ON);
        SerialPort.SetIoState(Constant.IOCTRL_PMU_RFID_ON);
    }

    /**
     * 下电
     */
    public void powerDown(){
        SerialPort.SetIoState(Constant.IOCTRL_PMU_BARCODE_OFF);
        SerialPort.SetIoState(Constant.IOCTRL_PMU_RFID_OFF);
    }


    /**
     * 打开串口
     * @return
     * @throws SecurityException
     * @throws IOException
     * @throws InvalidParameterException
     */
    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        Log.d("011", "打开串口1111");
        if (mSerialPort == null) {
			/* Read serial port parameters */
            Log.d("011", "打开串口1112");
//            SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            String path = Constant.device;
            int baudrate = Integer.decode(Constant.baudrate);
            int nbits = Integer.decode(Constant.bits);
            int nstop = Integer.decode(Constant.nstop);
            char cVerify = Constant.cverify.charAt(0);

            Log.d("011", "打开串口1113");
			/* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1) || nbits == -1
                    ||nstop == -1 || cVerify == 'C') {

                Log.d("011", "打开串口11130");
                throw new InvalidParameterException();
            }
            Log.d("011", "打开串口1114");
//            Log.d("012","Path:"+path+"   baudrate:"+baudrate);
			/* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, nbits, cVerify, nstop, 0);
            Log.d("011", "打开串口1115");
        }
        return mSerialPort;
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }
}
