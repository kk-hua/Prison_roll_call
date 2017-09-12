package com.shrw.duke.portlibrary.common;

/**
 * Created by rw-duke on 2017/5/17.
 */

public class Constant {

    public static final int TAG_18_TYPE = 18;
    public static final int TAG_19_TYPE = 19;
    public static final int TAG_30_TYPE = 30;

    public static final String DB_NAME="tags.db";

    public static final int SELECT_FILE_REQ_CODE = 1;
    public static final int SELECT_FILE_RESULT_CODE = 2;


    public static final String baudrate = "115200";
    public static final String cverify = "N";
    public static final String device = "/dev/ttyMT1";
//    public static final String device = "/dev/ttyMT0";
    public static final String bits = "8";
    public static final String nstop = "1";

    //RFID电源
    public static final int IOCTRL_PMU_RFID_ON = 0x03;
    public static final int IOCTRL_PMU_RFID_OFF = 0x04;
    //扫描头串口电源
    public static final int IOCTRL_PMU_BARCODE_ON = 0x05;
    public static final int IOCTRL_PMU_BARCODE_OFF = 0x06;
    //扫描头IO
    public static final int IOCTRL_PMU_BARCODE_TRIG_HIGH = 0x11;
    public static final int IOCTRL_PMU_BARCODE_TRIG_LOW = 0x12;
    //RFID IO
    public static final int IOCTRL_PMU_RFID_GPIOEXT_HIGH = 0x15;
    public static final int IOCTRL_PMU_RFID_GPIOEXT_LOW = 0x16;
    //底部串口电源
    public static final int IOCTRL_EXTERN_UART_ON = 0x07;
    public static final int IOCTRL_EXTERN_UART_OFF = 0x08;

    public static final String FILE_PATH = "file_name";

    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final String ACTUAL = "actual";
    public static final String NOTYET = "notyet";
    public static final String READ_ARG_CMD = "02030405000B00004400B5";
    public static final String TYPE_TYPE = "tag_type";
}
