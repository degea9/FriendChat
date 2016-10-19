package com.android.friendchat.message;

import com.android.friendchat.data.model.ChatMessage;

import android.net.Uri;

/**
 * Created by hp 400 on 10/18/2016.
 */
public interface MessageInteractor {
    interface OnFinishedListener {
        void retrieveMessage(ChatMessage message);
    }

    void uploadPhoto(Uri uri,String toId);
    void getMessages(String toId, OnFinishedListener callback);
}
