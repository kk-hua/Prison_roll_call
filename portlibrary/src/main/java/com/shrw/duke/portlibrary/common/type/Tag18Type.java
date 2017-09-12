package com.shrw.duke.portlibrary.common.type;

import android.util.Log;

import com.shrw.duke.portlibrary.common.bean.TagBean;
import com.shrw.duke.portlibrary.common.bean.type18.TagBase18;
import com.shrw.duke.portlibrary.common.interfaces.ITag;
import com.shrw.duke.portlibrary.common.utils.Parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rw-duke on 2017/6/28.
 */

public class Tag18Type implements ITag {
    private static final int TAG_LEN = 28;
    private Parse parse;

    public Tag18Type() {
        this.parse = new Parse();
    }

    @Override
    public TagBase18 parseTag(String data) {
        Log.d("标签数据18：",data);
        StringBuffer srcString = new StringBuffer(data);

        TagBean tagBean = parse.parseHeand(data);
//        Log.d("标签类型：",tagBean.getTagType()+"");
        if (tagBean != null) {
            TagBase18 tagBase18 = new TagBase18(tagBean);
            int count = tagBean.getCount();
            int index;
            List<TagBase18.Tag18> tag18List = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                TagBase18.Tag18 tag18 = new TagBase18.Tag18();
                index = (24 + i * TAG_LEN) + TAG_LEN;
                String tag = srcString.substring(index - TAG_LEN, index);

                tag18.setTagId(tag.substring(0, 6));

                String state = tag.substring(6, 8);
                tag18.setStatus(new TagBase18.Status18(state));
                tag18.setAct_Id1(tag.substring(8, 12));
                tag18.setAct_Rssi1(tag.substring(12, 14));
                tag18.setAct_Id2(tag.substring(14, 18));
                tag18.setAct_Rssi2(tag.substring(18, 20));
                tag18.setActivations(tag.substring(20, 22));
                tag18.setSendCount(tag.substring(22, 24));
                tag18.setHfRSSI1(tag.substring(24, 26));
                tag18.setHfRSSI2(tag.substring(26, 28));
                tag18List.add(tag18);
            }

            tagBase18.setTags(tag18List);
            return tagBase18;
        }

        return null;
    }


}
