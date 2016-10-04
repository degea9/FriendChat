package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.presenter.SingUpPresenter;
import com.android.friendchat.view.contract.SingUpContract;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity implements SingUpContract {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    @Bind(R.id.email)
    EditText txtEmail;
    @Bind(R.id.password)
    EditText txtPassword;
    @Bind(R.id.sign_up_button)
    Button btnSignUp;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private SingUpPresenter mSingUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mSingUpPresenter = new SingUpPresenter(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                mSingUpPresenter.signUp(email,password);
            }
        });
    }


    @Override
    public void navigateToLogin() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
