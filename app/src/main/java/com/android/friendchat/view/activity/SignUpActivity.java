package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.presenter.SingUpPresenter;
import com.android.friendchat.view.contract.SingUpContract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SingUpContract {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    @Bind(R.id.email)
    EditText txtEmail;
    @Bind(R.id.password)
    EditText txtPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private SingUpPresenter mSingUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mSingUpPresenter = new SingUpPresenter(this);

    }

    @OnClick(R.id.sign_up_button)
    public void signUp(){
        progressBar.setVisibility(View.VISIBLE);
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        mSingUpPresenter.signUp(email,password);
    }

    @OnClick(R.id.sign_in_button)
    public void signIn(){
       navigateTo(LoginActivity.class);
    }

    @Override
    public void singUpSuccess() {
        progressBar.setVisibility(View.GONE);
        navigateTo(LoginActivity.class);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
    }
}
