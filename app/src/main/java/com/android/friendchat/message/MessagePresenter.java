/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.message;

import com.android.friendchat.data.model.ChatMessage;

import android.net.Uri;

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

    public void senPhotoMessage(Uri uri,String toId){
        mInteractor.uploadPhoto(uri, toId);
    }

    public void senVideoMessage(Uri uri,String toId){
        mInteractor.uploadVideo(uri,toId);
    }

    public void getMessages(String toId){
        mInteractor.getMessages(toId, this);
    }

    @Override
    public void retrieveMessage(ChatMessage message) {
        mView.renderMessage(message);
    }


}
