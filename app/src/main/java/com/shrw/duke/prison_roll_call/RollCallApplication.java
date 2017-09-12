package com.shrw.duke.prison_roll_call;

import android.app.Application;
import android.content.Context;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.prison_roll_call.utils.PreferencesUtils;

/**
 * Created by rw-duke on 2017/9/11.
 */

public class RollCallApplication extends Application {
    private  Context mContext;
    public static String mFilePath;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mFilePath = PreferencesUtils.getString(mContext, Constant.FILE_PATH,null);
    }

    public Context getApplicationContext(){
        return mContext;
    }
}
