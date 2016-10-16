package com.android.friendchat.auth;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class AuthPresenter implements AuthInteractor {
    private static final String TAG = AuthPresenter.class.getSimpleName();
    private AuthContract.SignIn mSignInView;
    private AuthContract.SignUp mSignUpView;
    private AuthInteractorImpl mInteractor;

    public AuthPresenter(AuthContract.SignIn loginContract) {
        mSignInView = loginContract;
        mInteractor = new AuthInteractorImpl();
    }

    public AuthPresenter(AuthContract.SignUp signUpContract) {
        mSignUpView = signUpContract;
        mInteractor = new AuthInteractorImpl();
    }

    public void signIn(String email, String password) {
        mInteractor.login(email, password, this);
    }

    public void signInWithFacebook(String accessToken) {
        mInteractor.signInWithFacebook(accessToken, this);
    }

    public void signUp(String email, String password) {
        mInteractor.signUp(email, password, this);
    }

    @Override
    public void loginSuccess() {
        mSignInView.navigateToProfile();
    }

    @Override
    public void loginFailure() {
        mSignInView.showLoginFailureMessage();
    }

    @Override
    public void validateError(String errMessage) {
        if (mSignInView != null)
            mSignInView.showValidateErrorMessage(errMessage);
        if(mSignUpView!=null)
            mSignUpView.showValidateErrorMessage(errMessage);
    }

    @Override
    public void signUpSuccess() {
        mSignUpView.navigateToFillProfile();
    }

    @Override
    public void signUpFailure() {
        mSignUpView.showSignUpFailureMessage();
    }
}
