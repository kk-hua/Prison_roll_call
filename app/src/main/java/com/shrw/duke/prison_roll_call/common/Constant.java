package com.shrw.duke.prison_roll_call.common;

/**
 * Created by rw-duke on 2017/9/11.
 */

public class Constant {
    //选择文件
    public static final int SDCard_REQUEST_CODE = 0X01;
    public static final int SDCARD_RESULT_CODE = 0x02;

    //消息传递类型
    public static final int UNCALLED_TYPE = 0x03;//未到
    public static final int HAS_TO_TYPE = 0x04;//已到
    public static final int NOTE_TYPE = 0x05;//已到
    public static final int PORT_DATA_TYPE = 0x06;//已到

    //43指令
    public static final String SEND_CMD = "0203040500190000430000FF011F00050000001A011200FFBB";
}
