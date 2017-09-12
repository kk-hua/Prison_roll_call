package com.shrw.duke.portlibrary.common.interfaces;

/**
 * Created by rw-duke on 2017/6/28.
 */

public abstract class IGenerator {
    public abstract ITag createTagParse(String data, int tagType);
    public abstract ITag createTagParse(int tagType);
}
