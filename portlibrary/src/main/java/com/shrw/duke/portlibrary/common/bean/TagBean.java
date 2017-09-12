package com.shrw.duke.portlibrary.common.bean;

/**
 * Created by rw-duke on 2017/6/28.
 */

public class TagBean {
    public String head;         //头
    public int length;       //长度
    public String deviceID;     //设备id
    public String cmd;          //命令编号
    public String sn;           //数据包自增量
    public int count;           //标签有效个数
    public int tagType;      //标签类型
    public String checkSum;     //校验和


    public TagBean() {
    }

    public TagBean(TagBean tagBean) {
        head = tagBean.head;
        length =tagBean.length;
        deviceID = tagBean.deviceID;
        cmd = tagBean.cmd;
        sn = tagBean.sn;
        count = tagBean.count;
        tagType = tagBean.tagType;
        checkSum = tagBean.checkSum;
    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    @Override
    public String toString() {
        return "TagBean{" +
                "head='" + head + '\'' +
                ", length='" + length + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", cmd='" + cmd + '\'' +
                ", sn='" + sn + '\'' +
                ", count=" + count +
                ", tagType='" + tagType + '\'' +
                ", checkSum='" + checkSum + '\'' +
                '}';
    }
}
