package com.shrw.duke.prison_roll_call.utils;

import android.widget.Toast;

import com.shrw.duke.prison_roll_call.RollCallApplication;

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(RollCallApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}