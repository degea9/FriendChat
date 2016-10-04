package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.presenter.LoginPresenter;
import com.android.friendchat.view.contract.LoginContract;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract {
    @Bind(R.id.email)
    EditText txtEmail;
    @Bind(R.id.password)
    EditText txtPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void login(){
        progressBar.setVisibility(View.VISIBLE);
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        mPresenter.login(email,password);
    }


    @Override
    public void loginSuccess() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,"login success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFailure() {

    }
}
