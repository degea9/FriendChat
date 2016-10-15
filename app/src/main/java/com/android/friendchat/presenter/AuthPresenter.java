package com.android.friendchat.presenter;

import com.android.friendchat.interactor.AuthInteractor;
import com.android.friendchat.interactor.AuthInteractorImpl;
import com.android.friendchat.view.contract.AuthContract;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class AuthPresenter implements AuthInteractor{
    private static final String TAG = AuthPresenter.class.getSimpleName();
    private AuthContract mContract;
    private AuthInteractorImpl mInteractor;

    public AuthPresenter(AuthContract loginContract) {
        mContract = loginContract;
        mInteractor = new AuthInteractorImpl();
    }

    public void login(String email, String password) {
        mInteractor.login(email,password,this);
    }

    @Override
    public void loginSuccess() {
        mContract.navigateToProfile();
    }

    @Override
    public void loginFailure() {
        mContract.showLoginFailureMessage();
    }

    @Override
    public void validateError(String errMessage){
        mContract.showValidateErrorMessage(errMessage);
    }
}
