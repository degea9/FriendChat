package com.android.friendchat.signup;

/**
 * Created by APC on 10/16/2016.
 */

public class SignUpPresenter implements SignUpInteractor.OnFinishedListener {

    private static final String TAG = SignUpPresenter.class.getSimpleName();
    private SignUpView mSignUpView;
    private SignUpInteractorImpl mInteractor;


    public SignUpPresenter(SignUpView signUpView) {
        mSignUpView = signUpView;
        mInteractor = new SignUpInteractorImpl();
    }


    public void signUp(String username,String email, String password) {
        mInteractor.signUp(username,email, password, this);
    }


    @Override
    public void validateError(String errMessage) {
        mSignUpView.showValidateErrorMessage(errMessage);
    }

    @Override
    public void signUpSuccess() {
        mSignUpView.navigateToMain();
    }

    @Override
    public void signUpFailure(String reason) {
        mSignUpView.showSignUpFailureMessage(reason);
    }
}
