package com.android.friendchat.utils;

import android.util.Log;

/**
 * Created by hp 400 on 10/5/2016.
 */
public class LogUtil {
    private static final boolean DEGUB = true;
    public static final void d(String tag,String message){
        if(DEGUB)
            Log.d(tag, message);
    }
}
