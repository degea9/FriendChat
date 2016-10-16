package com.android.friendchat.signin;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.android.friendchat.R;
import com.android.friendchat.signup.SignUpFragment;
import com.android.friendchat.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addLoginFragment();
    }

    public void addLoginFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);
        transaction.replace(R.id.container,new SignInFragment());
        transaction.commit();
    }

    public void addSignUpFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);
        transaction.replace(R.id.container,new SignUpFragment());
        transaction.commit();
    }

}
