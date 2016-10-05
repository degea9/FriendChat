package com.android.friendchat;

import com.facebook.FacebookSdk;

import android.app.Application;

/**
 * Created by hp 400 on 10/5/2016.
 */
public class FriendChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
