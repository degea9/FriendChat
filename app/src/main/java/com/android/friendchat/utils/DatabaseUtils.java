/**
 * Created by tuandang on 10/6/2016.
 */
package com.android.friendchat.utils;

import com.android.friendchat.data.model.User;

public class DatabaseUtils {
    public static String getUserInfo(User user){
        String info = "";
        if(user.isMale()) info +="Male";
        else info +="Female";
        return info;
    }
}
