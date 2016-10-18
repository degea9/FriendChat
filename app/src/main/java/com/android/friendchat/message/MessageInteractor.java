package com.android.friendchat.message;

import com.android.friendchat.data.model.ChatMessage;

/**
 * Created by hp 400 on 10/18/2016.
 */
public interface MessageInteractor {
    interface OnFinishedListener {
        void retrieveMessage(ChatMessage message);
    }

    void getMessages(String toId, MessageInteractor.OnFinishedListener callback);
}
