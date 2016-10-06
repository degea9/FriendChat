package com.android.friendchat.view.activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.android.friendchat.R;
import com.android.friendchat.presenter.LoginPresenter;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.contract.LoginContract;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.email)
    EditText txtEmail;
    @Bind(R.id.password)
    EditText txtPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.fb_login_button)
    LoginButton fbLoginButton;
    LoginPresenter mPresenter;
    //FaceBook
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mPresenter = new LoginPresenter(this);
        mCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtil.d(TAG, "facebook:onSuccess:" + loginResult);
                Toast.makeText(getApplicationContext(),"facebook:onSuccess:",Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                LogUtil.d(TAG, "facebook:onCancel");
                Toast.makeText(getApplicationContext(),"facebook:onCancel",Toast.LENGTH_SHORT).show();
                // ...
            }

            @Override
            public void onError(FacebookException error) {

                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(getApplicationContext(),"facebook:onError",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Toast.makeText(getApplicationContext(),"handleFacebookAccessToken:" + token,Toast.LENGTH_SHORT).show();

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
    public void login(){
        progressBar.setVisibility(View.VISIBLE);
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        mPresenter.login(email, password);
    }

    public void facebookLogin(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginSuccess() {
        progressBar.setVisibility(View.GONE);
        navigateTo(ProfileActivity.class);
        Toast.makeText(this,"login success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFailure() {

    }


}
