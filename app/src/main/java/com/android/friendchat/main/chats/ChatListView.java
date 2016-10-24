package com.android.friendchat.main.chats;

import com.android.friendchat.data.model.ChatMessage;

/**
 * Created by APC on 10/24/2016.
 */

public interface ChatListView {
    void renderMessage(ChatMessage message);
}
