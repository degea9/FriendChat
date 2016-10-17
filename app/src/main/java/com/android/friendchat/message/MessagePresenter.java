/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.message;

public class MessagePresenter {
    private MessageInteractor mInteractor;
    public MessagePresenter(){
        mInteractor = new MessageInteractor(this);
    }
    public void senTextMessage(String message,String toId){
        mInteractor.senTextMessage(message,toId);
    }

    public void senPhotoMessage(String url,String toId){
        mInteractor.senPhotoMessage(url,toId);
    }
}
