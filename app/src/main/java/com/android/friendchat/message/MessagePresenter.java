/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.message;

import com.android.friendchat.data.model.ChatMessage;

public class MessagePresenter implements MessageInteractor.OnFinishedListener {
    private MessageInteractorImpl mInteractor;
    private MessagesView mView;
    public MessagePresenter(MessagesView view){
        mView = view;
        mInteractor = new MessageInteractorImpl(this);
    }
    public void senTextMessage(String message,String toId){
        mInteractor.senTextMessage(message, toId);
    }

    public void senPhotoMessage(String url,String toId){
        mInteractor.senPhotoMessage(url, toId);
    }

    public void getMessages(String toId){
        mInteractor.getMessages(toId,this);
    }

    @Override
    public void retrieveMessage(ChatMessage message) {
        mView.renderMessage(message);
    }
}
