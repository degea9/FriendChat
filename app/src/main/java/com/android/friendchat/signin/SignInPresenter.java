package com.android.friendchat.signin;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class SignInPresenter implements SignInInteractor.OnFinishedListener {
    private static final String TAG = SignInPresenter.class.getSimpleName();
    private SignInView mSignInView;
    private SignInInteractorImpl mInteractor;

    public SignInPresenter(SignInView signInView) {
        mSignInView = signInView;
        mInteractor = new SignInInteractorImpl();
    }

    public void signIn(String email, String password) {
        mInteractor.signIn(email, password, this);
    }

    public void signInWithFacebook(String accessToken) {
        mInteractor.signInWithFacebook(accessToken, this);
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
            mSignInView.showValidateErrorMessage(errMessage);
    }


}
