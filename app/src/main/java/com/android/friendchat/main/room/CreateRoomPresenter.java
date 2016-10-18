package com.android.friendchat.main.room;

import android.net.Uri;

import com.android.friendchat.data.model.Room;
import com.android.friendchat.interactor.RoomInteractor;

/**
 * Created by hp 400 on 10/12/2016.
 */
public class CreateRoomPresenter implements CreateRoomInteractor.OnFinishedListener {
    private static final String TAG = CreateRoomPresenter.class.getSimpleName();
    private CreateView mView;
    private CreateRoomInteractor mInteractor;
    public CreateRoomPresenter(CreateView view){
        mView = view;
        mInteractor = new CreateRoomInteractorImpl();
    }

    public void senTextMessage(String message,String toId){
        //mInteractor.senTextMessage(message,toId);
    }

    public void senPhotoMessage(String url,String toId){
        //mInteractor.senPhotoMessage(url,toId);
    }

    public void uploadThumbnail(Uri uri){
        mInteractor.uploadThumbnail(uri,this);
    }

    public void createRoom(String name,String thumbnailUrl){
        mInteractor.createRoom(name,thumbnailUrl,this);
    }

    @Override
    public void createSuccess() {
        mView.createSuccess();
    }

    @Override
    public void createFailure() {
        mView.showFailureMessage();
    }

    @Override
    public void uploadThumbnailSuccess(String returnedUrl) {
        mView.displayThumbnail(returnedUrl);
    }
}
