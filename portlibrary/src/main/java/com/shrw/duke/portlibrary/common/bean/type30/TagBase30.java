package com.shrw.duke.portlibrary.common.bean.type30;

import com.shrw.duke.portlibrary.common.bean.StatusBean;
import com.shrw.duke.portlibrary.common.bean.TagBean;
import com.shrw.duke.portlibrary.common.bean.type18.TagBase18;

import java.util.List;

/**
 * Created by rw-duke on 2017/6/29.
 */

public class TagBase30 extends TagBean {
    private List<Tag30> tags;

    public TagBase30(TagBean tagBean) {
        super(tagBean);
    }


    public List<Tag30> getTags() {
        return tags;
    }

    public void setTags(List<Tag30> tags) {
        this.tags = tags;
    }


    public static class Tag30 {
        private String tagId;
        private Status30 status;
        private String Act_ID;
        private String Act_Rssi;
        private String HfRssi;
        private String HfRssi2;

        public String getHfRssi2() {
            return HfRssi2;
        }

        public void setHfRssi2(String hfRssi2) {
            HfRssi2 = hfRssi2;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public Status30 getStatus() {
            return status;
        }

        public void setStatus(Status30 status) {
            this.status = status;
        }

        public String getAct_ID() {
            return Act_ID;
        }

        public void setAct_ID(String act_ID) {
            Act_ID = act_ID;
        }

        public String getAct_Rssi() {
            return Act_Rssi;
        }

        public void setAct_Rssi(String act_Rssi) {
            Act_Rssi = act_Rssi;
        }

        public String getHfRssi() {
            return HfRssi;
        }

        public void setHfRssi(String hfRssi) {
            HfRssi = hfRssi;
        }

        @Override
        public String toString() {
            return "Tag30{" +
                    "tagId='" + tagId + '\'' +
                    ", status=" + status +
                    ", Act_ID='" + Act_ID + '\'' +
                    ", Act_Rssi='" + Act_Rssi + '\'' +
                    ", HfRssi='" + HfRssi + '\'' +
                    ", HfRssi2='" + HfRssi2 + '\'' +
                    '}';
        }
    }

    public static class Status30 extends TagBase18.Status18 {

        public Status30(String state) {
            super(state);
        }
    }
}
