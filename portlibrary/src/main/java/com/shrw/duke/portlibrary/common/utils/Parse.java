package com.shrw.duke.portlibrary.common.utils;

import android.text.TextUtils;

import com.shrw.duke.portlibrary.common.bean.TagBean;

/**
 * Created by rw-duke on 2017/6/27.
 */

public class Parse {
    private static final String HEAD = "02030405";


    /**
     * 数据包校验
     * @param str
     * @return
     */
    public boolean verify(String str) {
        int vv = Integer.parseInt(
                str.substring(str.length() - 2, str.length()), 16);
        int cv = 0;
        for (int i = 0; i < str.length() - 2; i += 2) {
            cv += Integer.parseInt(str.substring(i, i + 2), 16);
        }
        cv &= 0xFF;
        if (vv == cv) return true;
        else return false;

    }

    public TagBean parseHeand(String srcString){
        String head = srcString.substring(0,8);
        int dataLength = srcString.length();
        String len = srcString.substring(8,12);
        int length = Integer.parseInt(len,16);
        if (filterDataPackage(head,dataLength,length)){
            TagBean tagBean = new TagBean();
            tagBean.setHead(head);
            tagBean.setLength(length);
            tagBean.setDeviceID(srcString.substring(12,16));
            tagBean.setCmd(srcString.substring(16,18));
            tagBean.setSn(srcString.substring(18,20));

            int type = Integer.parseInt(srcString.substring(20,22),16);
            tagBean.setTagType(type);
            int count = Integer.parseInt(srcString.substring(22,24),16);
            tagBean.setCount(count);
            tagBean.setCheckSum(srcString.substring(dataLength-2,dataLength));
            return tagBean;
        }
        return null;
    }

    /**
     * 同过头和长度过滤数据包
     * @param head    目标字符串
     * @param filterStr 过滤字符串
     * @param length    源字符串长度
     * @return  true表示是需要的数据包， false反之。
     */
    public boolean filterDataPackage(String head, String filterStr, int dataLength, int length){
        if (head.equals(filterStr)&&dataLength == length*2){
            return true;
        }
        return false;
    }

    public boolean filterDataPackage(String head, int dataLength, int length){
        return filterDataPackage(head,HEAD,dataLength,length);
    }

    // 十六进制转二进制
    public static String HToB(String a) {
        String s = Integer.toBinaryString(Integer.parseInt(a, 16));
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 8 - s.length(); i++) {
            stringBuffer.append("0");
        }
        stringBuffer.append(s);
        return stringBuffer.toString();
    }


    /**
     * 匹配6位16进制
     * @param str
     * @return
     */
    public static String complieHex(String str){
        try{
            String hexStr = Integer.toHexString(Integer.parseInt(str));


            if (hexStr.length()<6){
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 6 - hexStr.length(); i++) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(hexStr);
                return stringBuilder.toString();
            }
            return str;
        }catch (NumberFormatException e){
            return str;
        }
    }

    public static String toHexString(byte[] byteArray, int size) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException(
                    "this byteArray must not be null or empty");
        final StringBuilder hexString = new StringBuilder(2 * size);
        for (int i = 0; i < size; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
//            if (i != (byteArray.length - 1))
//                hexString.append(" ");
        }
        return hexString.toString().toUpperCase();
    }



    /**
     * hexString转byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar转int
     *
     * @param hexChar hex单个字节
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static String toHex(Object o){
        String s = String.valueOf(o);
        return toHex2(s);
    }

    private static String toHex2(String text){
        if (TextUtils.isEmpty(text))
            return "null";
        int i = Integer.parseInt(text);
        String hex = Integer.toHexString(i);
        if (hex.length()<2){
            return "0"+hex;
        }
        return hex;
    }
}
