package com.android.friendchat.signup;

/**
 * Created by APC on 10/16/2016.
 */

public interface SignUpView {
    void navigateToMain();
    void showSignUpFailureMessage(String reason);
    void showValidateErrorMessage(String errMessage);
}
