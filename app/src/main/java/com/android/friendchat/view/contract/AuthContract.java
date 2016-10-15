package com.android.friendchat.view.contract;

/**
 * Created by hp 400 on 10/4/2016.
 */
public interface AuthContract {
    void navigateToProfile();
    void showLoginFailureMessage();
    void showValidateErrorMessage(String errMessage);
}
