package com.shrw.duke.portlibrary.common.bean.type18;

import com.shrw.duke.portlibrary.common.bean.TagBean;
import com.shrw.duke.portlibrary.common.utils.Parse;

import java.util.List;

/**
 * 18类型
 * Created by rw-duke on 2017/6/28.
 */

public class TagBase18 extends TagBean {

    public List<Tag18> tags;

    public TagBase18(TagBean tagBean) {
        super(tagBean);
    }

    public List<Tag18> getTags() {
        return tags;
    }

    public void setTags(List<Tag18> tags) {
        this.tags = tags;
    }

    public static class Tag18{
        private String tagId;
        private Status18 status;
        private String Act_Id1;//激活ID1
        private String Act_Rssi1;//激活场强1
        private String Act_Id2;//激活ID2
        private String Act_Rssi2;//激活场强2
        private String activations;//激活次数
        private String sendCount;//2.4G发送次数
        private String HfRSSI1;//HF场强1
        private String HfRSSI2;//HF场强2

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public Status18 getStatus() {
            return status;
        }

        public void setStatus(Status18 status) {
            this.status = status;
        }

        public String getAct_Id1() {
            return Act_Id1;
        }

        public void setAct_Id1(String act_Id1) {
            Act_Id1 = act_Id1;
        }

        public String getAct_Rssi1() {
            return Act_Rssi1;
        }

        public void setAct_Rssi1(String act_Rssi1) {
            Act_Rssi1 = act_Rssi1;
        }

        public String getAct_Id2() {
            return Act_Id2;
        }

        public void setAct_Id2(String act_Id2) {
            Act_Id2 = act_Id2;
        }

        public String getAct_Rssi2() {
            return Act_Rssi2;
        }

        public void setAct_Rssi2(String act_Rssi2) {
            Act_Rssi2 = act_Rssi2;
        }


        public String getActivations() {
            return activations;
        }

        public void setActivations(String activations) {
            this.activations = activations;
        }

        public String getSendCount() {
            return sendCount;
        }

        public void setSendCount(String sendCount) {
            this.sendCount = sendCount;
        }

        public String getHfRSSI1() {
            return HfRSSI1;
        }

        public void setHfRSSI1(String hfRSSI1) {
            HfRSSI1 = hfRSSI1;
        }

        public String getHfRSSI2() {
            return HfRSSI2;
        }

        public void setHfRSSI2(String hfRSSI2) {
            HfRSSI2 = hfRSSI2;
        }


        @Override
        public String toString() {
            return "Tag18{" +
                    "tagId='" + tagId + '\'' +
                    ", status='" + status + '\'' +
                    ", Act_Id1='" + Act_Id1 + '\'' +
                    ", Act_Rssi1='" + Act_Rssi1 + '\'' +
                    ", Act_Id2='" + Act_Id2 + '\'' +
                    ", Act_Rssi2='" + Act_Rssi2 + '\'' +
                    ", activations='" + activations + '\'' +
                    ", sendCount='" + sendCount + '\'' +
                    ", HfRSSI1='" + HfRSSI1 + '\'' +
                    ", HfRSSI2='" + HfRSSI2 + '\'' +
                    '}';
        }
    }


    public static class Status18{
        private int actionZone;//激活区
        private int voltage;//电压
        private int tamper;//防拆
        private int btn1;//按钮
        private int vibration;//振动
        private String gain;//增益
        private int traverse;//穿越

        public Status18(String src) {
            String binary = Parse.HToB(src);
            actionZone = Integer.parseInt(binary.substring(0,1));
            voltage = Integer.parseInt(binary.substring(1,2));
            tamper = Integer.parseInt(binary.substring(2,3));
            btn1 = Integer.parseInt(binary.substring(3,4));
            vibration = Integer.parseInt(binary.substring(4,5));
            gain = binary.substring(5,7);
            traverse = Integer.parseInt(binary.substring(7,8));
        }

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

        public String getGain() {
            return gain;
        }

        public void setGain(String gain) {
            this.gain = gain;
        }

        public int getTraverse() {
            return traverse;
        }

        public void setTraverse(int traverse) {
            this.traverse = traverse;
        }

        @Override
        public String toString() {
            return "Status18{" +
                    "actionZone=" + actionZone +
                    ", voltage=" + voltage +
                    ", tamper=" + tamper +
                    ", btn1=" + btn1 +
                    ", vibration=" + vibration +
                    ", gain='" + gain + '\'' +
                    ", traverse=" + traverse +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TagBase18{" +
                "tags=" + tags +
                '}';
    }
}
