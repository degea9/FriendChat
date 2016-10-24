package com.android.friendchat.main.chats;

import com.android.friendchat.data.model.ChatMessage;

/**
 * Created by APC on 10/24/2016.
 */

public class ChatListPresenter implements ChatListInteractor.OnFinishedListener {
    private ChatListView mView;
    private ChatListInteractor mChatListInteractor;
    public ChatListPresenter(ChatListView view){
        mView = view;
        mChatListInteractor = new ChatListInteractorImpl();
    }

    public void getChatList(){
        mChatListInteractor.getChatList(this);
    }
    @Override
    public void retrieveChatList(ChatMessage message) {
        mView.renderMessage(message);
    }
}
