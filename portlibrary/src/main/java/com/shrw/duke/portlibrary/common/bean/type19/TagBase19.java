package com.shrw.duke.portlibrary.common.bean.type19;

import com.shrw.duke.portlibrary.common.bean.TagBean;
import com.shrw.duke.portlibrary.common.bean.type18.TagBase18;
import com.shrw.duke.portlibrary.common.bean.type30.TagBase30;

import java.util.List;

/**
 * Created by rw-duke on 2017/6/30.
 */

public class TagBase19 extends TagBean {
    private List<Tag19> tags;

    public TagBase19(TagBean tagBean) {
        super(tagBean);
    }

    public List<Tag19> getTags() {
        return tags;
    }

    public void setTags(List<Tag19> tags) {
        this.tags = tags;
    }

    public static class Tag19 extends TagBase18.Tag18{
        private String flag;
        private String content;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public void setHfRSSI1(String hfRSSI1) {
            super.setHfRSSI1(hfRSSI1);
        }

        @Override
        public String getHfRSSI1() {
            return super.getHfRSSI1();
        }

        @Override
        public void setAct_Rssi2(String act_Rssi2) {
            super.setAct_Rssi2(act_Rssi2);
        }

        @Override
        public String getHfRSSI2() {
            return super.getHfRSSI2();
        }

        @Override
        public String toString() {
            return "Tag19{" +
                    "flag='" + flag + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TagBase19{" +
                "tags=" + tags +
                '}';
    }
}
