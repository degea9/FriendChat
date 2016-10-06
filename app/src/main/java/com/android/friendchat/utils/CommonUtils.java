package com.android.friendchat.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hp 400 on 10/6/2016.
 */
public class CommonUtils {
    public static String getDateString(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }
}
