package com.shrw.duke.portlibrary.common.type;

import android.util.Log;

import com.shrw.duke.portlibrary.common.bean.TagBean;
import com.shrw.duke.portlibrary.common.bean.type19.TagBase19;
import com.shrw.duke.portlibrary.common.bean.type30.TagBase30;
import com.shrw.duke.portlibrary.common.interfaces.ITag;
import com.shrw.duke.portlibrary.common.utils.Parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rw-duke on 2017/7/12.
 */

public class Tag19Type implements ITag {
    private static final int TAG_LEN = 22 * 2;
    private Parse parse;

    public Tag19Type() {
        this.parse = new Parse();
    }

    @Override
    public Object parseTag(String data) {
        Log.d("标签数据19：", data);
        TagBean tagBean = parse.parseHeand(data);
        Log.d("标签类型：", tagBean.getTagType() + "");
        if (tagBean != null) {
            TagBase19 tagBase19 = new TagBase19(tagBean);
            int count = tagBean.getCount();
            int index;
            List<TagBase19.Tag19> tag19List = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                TagBase19.Tag19 tag19 = new TagBase19.Tag19();
                index = (24 + i * TAG_LEN) + TAG_LEN;
                String tag = data.substring(index - TAG_LEN, index);
                Log.d("标签数据19tag:", tag);
                Log.d("标签数据19tagID1:", tag.substring(0, 12));
                Log.d("标签数据19tagID2:", reverse(tag.substring(0, 12)));
                tag19.setTagId(reverse(tag.substring(0, 12)));
                tag19.setFlag(tag.substring(12, 14));
                tag19.setContent(tag.substring(14, 40));
                tag19.setHfRSSI1(tag.substring(40, 42));
                tag19.setHfRSSI2(tag.substring(42, 44));

                tag19List.add(tag19);

            }
            tagBase19.setTags(tag19List);
            return tagBase19;
        }
        return null;
    }

    private String reverse(String id) {
        StringBuilder stringBuilder = new StringBuilder(id);
        StringBuilder sb = new StringBuilder();
        int length = stringBuilder.length();
        if (length % 2 != 0)
            return null;

        for (int i = length; i > 0; i = i - 2) {
            sb.append(stringBuilder.substring(i-2, i));
        }

        return sb.toString();
    }
}
