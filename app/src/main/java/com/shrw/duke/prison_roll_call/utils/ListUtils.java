package com.shrw.duke.prison_roll_call.utils;

import com.shrw.duke.prison_roll_call.entity.PeopleRoll;

import java.util.List;

/**
 * Created by rw-duke on 2017/9/13.
 */

public class ListUtils {

//    public static boolean contains(String id){
//        return indexOf(id)>=0;
//    }
    public static boolean contains(List<PeopleRoll> list, String id){
        return indexOf(list,id)>=0;
    }

    public static int indexOf(List<PeopleRoll> list,String id) {
        int size = list.size();
        PeopleRoll peopleRoll;
        String rfid;
        if (id == null) {
            for (int i = 0; i < size; i++){
                peopleRoll = list.get(i);
                rfid = peopleRoll.getRfid();
                if (rfid == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++){
                peopleRoll = list.get(i);
                rfid = peopleRoll.getRfid();
                if (rfid.equals(id))
                    return i;
            }
        }
        return -1;
    }
}
