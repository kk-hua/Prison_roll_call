package com.shrw.duke.prison_roll_call.entity;

import java.util.List;

/**
 * 考勤报告
 * Created by rw-duke on 2017/9/14.
 */

public class Note {
    private int hasToNum;//已到人数
    private int uncalledNum;//未到人数
    private List<PeopleRoll> uncalledNote;  //未到备注

    public int getHasToNum() {
        return hasToNum;
    }

    public void setHasToNum(int hasToNum) {
        this.hasToNum = hasToNum;
    }

    public int getUncalledNum() {
        return uncalledNum;
    }

    public void setUncalledNum(int uncalledNum) {
        this.uncalledNum = uncalledNum;
    }

    public List<PeopleRoll> getUncalledNote() {
        return uncalledNote;
    }

    public void setUncalledNote(List<PeopleRoll> uncalledNote) {
        this.uncalledNote = uncalledNote;
    }
}
