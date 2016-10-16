/**
 * Created by tuandang on 10/5/2016.
 */
package com.android.friendchat.presenter;

import com.android.friendchat.data.model.User;
import com.android.friendchat.interactor.ProfileInteractor;
import com.android.friendchat.view.contract.ProfileContract;

public class ProfilePresenter {
    ProfileInteractor mProfileInteractor;
    ProfileContract mContract;

    public ProfilePresenter() {
        mProfileInteractor = new ProfileInteractor();
    }

    public void setViewContract(ProfileContract profileContract) {
        mContract = profileContract;
    }

    public void saveProfile(User user) {
        mProfileInteractor.saveProfile(user);
    }

    public void getProfile() {
        mProfileInteractor.setPresenter(this);
        mProfileInteractor.getProfile();
    }

    public void displayProfile(User user) {
        if (mContract != null)
            mContract.displayProfile(user);
    }
}
