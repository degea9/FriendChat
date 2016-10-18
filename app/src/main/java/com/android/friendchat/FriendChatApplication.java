package com.android.friendchat;

import com.facebook.FacebookSdk;

import android.app.Application;

/**
 * Created by hp 400 on 10/5/2016.
 */
public class FriendChatApplication extends Application {
    private static FriendChatApplication instance;
    public static FriendChatApplication get() { return instance; }
    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

}
