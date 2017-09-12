package com.shrw.duke.portlibrary.common.bean;

/**
 * Created by rw-duke on 2017/6/29.
 */

public class StatusBean {
    private int actionZone;//激活区
    private int voltage;//电压
    private int tamper;//防拆
    private int btn1;//按钮
    private int vibration;//振动
    private String retain;//保留

    public int getActionZone() {
        return actionZone;
    }

    public void setActionZone(int actionZone) {
        this.actionZone = actionZone;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getTamper() {
        return tamper;
    }

    public void setTamper(int tamper) {
        this.tamper = tamper;
    }

    public int getBtn1() {
        return btn1;
    }

    public void setBtn1(int btn1) {
        this.btn1 = btn1;
    }

    public int getVibration() {
        return vibration;
    }

    public void setVibration(int vibration) {
        this.vibration = vibration;
    }

    public String getRetain() {
        return retain;
    }

    public void setRetain(String retain) {
        this.retain = retain;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "actionZone=" + actionZone +
                ", voltage=" + voltage +
                ", tamper=" + tamper +
                ", btn1=" + btn1 +
                ", vibration=" + vibration +
                ", retain='" + retain + '\'' +
                '}';
    }
}
