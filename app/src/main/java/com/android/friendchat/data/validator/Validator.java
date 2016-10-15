package com.android.friendchat.data.validator;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class Validator {
    /**
     * validate email and password.
     * They must not empty and password length must be greater than 6 characters
     * @param email
     * @param password
     * @return
     */
    public static boolean validate(String email,String password){
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            return false ;
        }

        if (password.length() < 6) {
            return false;
        }
        return true;
    }
}
