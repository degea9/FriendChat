package com.android.friendchat.splash;

import android.os.Bundle;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.main.RoomActivity;
import com.android.friendchat.signin.LoginActivity;

public class SplashActivity extends BaseActivity implements SplashView {
    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();

    }

    @Override
    public void navigateToLogin() {
        navigateTo(LoginActivity.class);
    }

    @Override
    public void navigateToMain() {
        navigateTo(RoomActivity.class);
    }
}
