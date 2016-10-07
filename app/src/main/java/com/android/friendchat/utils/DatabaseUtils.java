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
    public static String getUserInfo(User user){
        String info = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = df.parse(user.getBirthDay());
            info+= Calendar.getInstance().get(Calendar.YEAR)- startDate.getYear();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            if (user.isMale()) info += " Male";
            else info += " Female";
            return info;
        }
    }
}
