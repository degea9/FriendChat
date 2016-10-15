package com.android.friendchat.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.friendchat.R;
import com.android.friendchat.view.activity.BaseActivity;
import com.android.friendchat.view.activity.ProfileActivity;
import com.android.friendchat.view.contract.AuthContract;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements AuthContract {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @Bind(R.id.input_email)
    EditText edtEmail;
    @Bind(R.id.input_password)
    EditText edtPassword;
    AuthPresenter mPresenter;
    //FaceBook
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new AuthPresenter(this);
        setupView();
//        mAuth = FirebaseAuth.getInstance();
//        mCallbackManager = CallbackManager.Factory.create();
//        fbLoginButton.setReadPermissions("email", "public_profile");
//        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                LogUtil.d(TAG, "facebook:onSuccess:" + loginResult);
//                Toast.makeText(getApplicationContext(),"facebook:onSuccess:",Toast.LENGTH_SHORT).show();
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                LogUtil.d(TAG, "facebook:onCancel");
//                Toast.makeText(getApplicationContext(),"facebook:onCancel",Toast.LENGTH_SHORT).show();
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//                Log.d(TAG, "facebook:onError", error);
//                Toast.makeText(getApplicationContext(),"facebook:onError",Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void setupView() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayoutEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayoutPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Toast.makeText(getApplicationContext(), "handleFacebookAccessToken:" + token, Toast.LENGTH_SHORT).show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        Toast.makeText(getApplicationContext(), "signInWithCredential:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication success" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @OnClick(R.id.btn_login)
    public void login() {
        showProgressDialog(getString(R.string.authenticating));
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        mPresenter.login(email, password);
    }

    @OnClick(R.id.fb_login_button)
    public void facebookLogin() {
    }

    @Override
    public void navigateToProfile() {
        dissmissProgressDialog();
        navigateTo(ProfileActivity.class);
    }

    @Override
    public void showLoginFailureMessage() {
        dissmissProgressDialog();
        showMessageDialog(getString(R.string.login_failure));
    }

    @Override
    public void showValidateErrorMessage(String errMessage) {
        dissmissProgressDialog();
        inputLayoutEmail.setError(errMessage);
        inputLayoutPassword.setError(errMessage);
    }


}
