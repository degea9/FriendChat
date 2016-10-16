package com.android.friendchat.splash;


import android.support.annotation.NonNull;

import com.android.friendchat.base.BasePresenter;
import com.android.friendchat.utils.LogUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by APC on 10/16/2016.
 */

public class SplashPresenter implements BasePresenter {
    private static final String TAG = SplashPresenter.class.getSimpleName();
    private SplashView mSplashView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public SplashPresenter(SplashView view) {
        mSplashView = view;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    LogUtil.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    mSplashView.navigateToMain();
                } else {
                    // User is signed out
                    LogUtil.d(TAG, "onAuthStateChanged:signed_out");
                    mSplashView.navigateToLogin();
                }
            }
        };
    }

    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
