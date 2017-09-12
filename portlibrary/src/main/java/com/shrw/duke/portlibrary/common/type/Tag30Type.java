package com.shrw.duke.portlibrary.common.type;

import android.util.Log;

import com.shrw.duke.portlibrary.common.bean.TagBean;
import com.shrw.duke.portlibrary.common.bean.type18.TagBase18;
import com.shrw.duke.portlibrary.common.bean.type30.TagBase30;
import com.shrw.duke.portlibrary.common.interfaces.ITag;
import com.shrw.duke.portlibrary.common.utils.Parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rw-duke on 2017/6/29.
 */

public class Tag30Type implements ITag {
    private static final int TAG_LEN = 18;
    private Parse parse;

    public Tag30Type() {
        this.parse = new Parse();
    }

    @Override
    public TagBase30 parseTag(String data) {
        Log.d("标签数据30：",data);
        TagBean tagBean = parse.parseHeand(data);
        Log.d("标签类型：",tagBean.getTagType()+"");
        if (tagBean!=null){
            TagBase30 tagBase30 = new TagBase30(tagBean);
            int count = tagBean.getCount();
            int index;
            List<TagBase30.Tag30> tag30List = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                TagBase30.Tag30 tag30 = new TagBase30.Tag30();
                index = (24 + i * TAG_LEN) + TAG_LEN;
                String tag = data.substring(index - TAG_LEN, index);
                Log.d("标签数据30tag:",tag);
                tag30.setTagId(tag.substring(0, 6));
                String state = tag.substring(6, 8);
                tag30.setStatus(new TagBase30.Status30(state));
                tag30.setAct_ID(tag.substring(8, 12));
                tag30.setAct_Rssi(tag.substring(12, 14));
                tag30.setHfRssi(tag.substring(14,16));
                tag30.setHfRssi2(tag.substring(16,18));

                tag30List.add(tag30);

            }
            tagBase30.setTags(tag30List);
            return tagBase30;
        }
        return null;
    }
}
