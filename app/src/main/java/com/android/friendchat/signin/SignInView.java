package com.android.friendchat.signin;

/**
 * Created by hp 400 on 10/4/2016.
 */
public interface SignInView {

    void navigateToProfile();

    void showLoginFailureMessage();

    void showValidateErrorMessage(String errMessage);


}
