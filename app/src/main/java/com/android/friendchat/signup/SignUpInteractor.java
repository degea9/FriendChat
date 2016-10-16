package com.android.friendchat.signup;

/**
 * Created by APC on 10/16/2016.
 */

public interface SignUpInteractor {
    interface OnFinishedListener {
        void validateError(String errMessage);

        void signUpSuccess();

        void signUpFailure();

    }

    void signUp(String email, String password, final OnFinishedListener callback);
}
