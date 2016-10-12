package com.android.friendchat.presenter;

import com.android.friendchat.interactor.RoomInteractor;

/**
 * Created by hp 400 on 10/12/2016.
 */
public class RoomPresenter {
    private RoomInteractor mInteractor;
    public RoomPresenter(){
        mInteractor = new RoomInteractor();
    }
}
