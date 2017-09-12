package com.shrw.duke.portlibrary.common.utils;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.portlibrary.common.interfaces.IGenerator;
import com.shrw.duke.portlibrary.common.interfaces.ITag;
import com.shrw.duke.portlibrary.common.type.Tag18Type;
import com.shrw.duke.portlibrary.common.type.Tag19Type;
import com.shrw.duke.portlibrary.common.type.Tag30Type;

/**
 * Created by rw-duke on 2017/6/28.
 */

public class TagGenerator extends IGenerator {
    private static TagGenerator tagGenerator;

    public static TagGenerator getInstance() {
        if (null == tagGenerator) {
            synchronized (TagGenerator.class) {
                if (null == tagGenerator) {
                    tagGenerator = new TagGenerator();
                }
            }
        }
        return tagGenerator;
    }

    @Override
    public ITag createTagParse(String data, int tagType) {
        switch (tagType){
            case Constant.TAG_18_TYPE:
                return new Tag18Type();
            case Constant.TAG_19_TYPE:
                return new Tag19Type();
            case Constant.TAG_30_TYPE:
                return new Tag30Type();
        }
        return null;
    }

    @Override
    public ITag createTagParse(int tagType) {
        switch (tagType){
            case Constant.TAG_18_TYPE:
                return new Tag18Type();
            case Constant.TAG_19_TYPE:
                return new Tag19Type();
            case Constant.TAG_30_TYPE:
                return new Tag30Type();
        }
        return null;
    }
}
