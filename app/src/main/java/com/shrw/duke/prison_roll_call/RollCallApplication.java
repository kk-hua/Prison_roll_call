package com.shrw.duke.prison_roll_call;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.prison_roll_call.utils.PreferencesUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rw-duke on 2017/9/11.
 */

public class RollCallApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();

    private static RollCallApplication mContext;
    public static String mFilePath;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mFilePath = PreferencesUtils.getString(mContext, Constant.FILE_PATH,null);
    }

    public static RollCallApplication getContext(){
        return mContext;
    }


    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish

    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

        System.exit(0);

    }
}
