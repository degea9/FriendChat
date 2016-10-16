package com.android.friendchat.signin;

/**
 * Created by APC on 10/15/2016.
 */

public interface SignInInteractor {
    interface OnFinishedListener {
        void loginSuccess();

        void loginFailure();

        void validateError(String errMessage);
    }

    void signIn(String email, String password, OnFinishedListener callback);
    void signInWithFacebook(String accessToken, final OnFinishedListener callback);

}
