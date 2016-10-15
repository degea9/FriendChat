package com.android.friendchat.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    //FaceBook
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupFragment();
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

    private void setupFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new LoginFragment());
        transaction.commit();
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




}
