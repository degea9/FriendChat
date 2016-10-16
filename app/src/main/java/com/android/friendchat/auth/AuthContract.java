package com.android.friendchat.auth;

/**
 * Created by hp 400 on 10/4/2016.
 */
public interface AuthContract {

    interface SignIn{
        void navigateToProfile();
        void showLoginFailureMessage();
        void showValidateErrorMessage(String errMessage);
    }

    interface SignUp{
        void navigateToFillProfile();
        void showSignUpFailureMessage();
        void showValidateErrorMessage(String errMessage);
    }

}
