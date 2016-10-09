/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.presenter;

import com.android.friendchat.data.model.TextMessage;
import com.android.friendchat.interactor.ChatInteractor;

public class ChatPresenter extends BasePresenter {
    private ChatInteractor mInteractor;
    public ChatPresenter(){
        mInteractor = new ChatInteractor(this);
    }
    public void senTextMessage(String message,String toId){
        mInteractor.senTextMessage(message,toId);
    }
}
