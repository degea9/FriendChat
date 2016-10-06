/**
 * Created by tuandang on 10/5/2016.
 */
package com.android.friendchat.presenter;

import com.android.friendchat.data.model.User;
import com.android.friendchat.interactor.ProfileInteractor;

public class ProfilePresenter extends BasePresenter {
    ProfileInteractor mProfileInteractor;

    public ProfilePresenter() {
        mProfileInteractor = new ProfileInteractor();
    }

    public void saveProfile(User user){
        mProfileInteractor.saveProfile(user);
    }
}
