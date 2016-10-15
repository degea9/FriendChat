package com.android.friendchat.auth;

/**
 * Created by APC on 10/15/2016.
 */

public interface AuthInteractor {
    void loginSuccess();

    void loginFailure();

    void validateError(String errMessage);
}
