/**
 * Created by tuandang on 10/6/2016.
 */
package com.android.friendchat.utils;

import com.android.friendchat.data.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseUtils {
    private static final String TAG = DatabaseUtils.class.getSimpleName();
    public static String getUserInfo(User user){
        String info = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = df.parse(user.getBirthDay());
            Calendar cal = Calendar.getInstance();
            LogUtil.d(TAG,"year "+cal.get(Calendar.YEAR));
            cal.setTime(startDate);
            info+= Calendar.getInstance().get(Calendar.YEAR)- cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            if (user.isMale()) info += " Male";
            else info += " Female";
            return info;
        }
    }
}
