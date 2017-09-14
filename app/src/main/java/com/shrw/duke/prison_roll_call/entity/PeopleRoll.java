package com.shrw.duke.prison_roll_call.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rw-duke on 2017/9/12.
 */

public class PeopleRoll implements Parcelable{
    private String name;    //人名
    private String carid;   //13.5M编号
    private String rfid;    //有源编号
    private String room;    //归属区中文名称
    private String type;       //0：犯人  1：警员
    private String level;      //管理级别
    private String note;

    public PeopleRoll() {
    }

    public PeopleRoll(String name, String carid,  String rfid, String room, String type, String level) {
        this.name = name;
        this.carid = carid;
        this.rfid = rfid;
        this.room = room;
        this.type = type;
        this.level = level;
    }

    public PeopleRoll(String name, String carid,  String rfid, String room, String type, String level, String note) {
        this.name = name;
        this.carid = carid;
        this.rfid = rfid;
        this.room = room;
        this.type = type;
        this.level = level;
        this.note = note;
    }


    protected PeopleRoll(Parcel in) {
        name = in.readString();
        carid = in.readString();
        rfid = in.readString();
        room = in.readString();
        type = in.readString();
        level = in.readString();
        note = in.readString();
    }

    public static final Creator<PeopleRoll> CREATOR = new Creator<PeopleRoll>() {
        @Override
        public PeopleRoll createFromParcel(Parcel in) {
            return new PeopleRoll(in);
        }

        @Override
        public PeopleRoll[] newArray(int size) {
            return new PeopleRoll[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "PeopleRoll{" +
                "name='" + name + '\'' +
                ", carid='" + carid + '\'' +
                ", rfid='" + rfid + '\'' +
                ", room='" + room + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(carid);
        dest.writeString(rfid);
        dest.writeString(room);
        dest.writeString(type);
        dest.writeString(level);
        dest.writeString(note);
    }
}
