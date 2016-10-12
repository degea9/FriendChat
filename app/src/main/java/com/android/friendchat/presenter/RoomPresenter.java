package com.android.friendchat.presenter;

import com.android.friendchat.data.model.Room;
import com.android.friendchat.interactor.RoomInteractor;

/**
 * Created by hp 400 on 10/12/2016.
 */
public class RoomPresenter {
    private RoomInteractor mInteractor;
    public RoomPresenter(){
        mInteractor = new RoomInteractor(this);
    }

    public void senTextMessage(String message,String toId){
        mInteractor.senTextMessage(message,toId);
    }

    public void senPhotoMessage(String url,String toId){
        mInteractor.senPhotoMessage(url,toId);
    }

    public void createRoom(Room room){
        mInteractor.createRoom(room);
    }
}
