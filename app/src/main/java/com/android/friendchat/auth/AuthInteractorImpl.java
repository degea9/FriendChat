package com.android.friendchat.auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.friendchat.data.model.User;
import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.utils.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by APC on 10/15/2016.
 */

public class AuthInteractorImpl {
    private static final String TAG = AuthInteractorImpl.class.getSimpleName();
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    public AuthInteractorImpl() {
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }

    public void login(String email, String password, final AuthInteractor callback) {
        if (Validator.validate(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    LogUtil.d(TAG, "task.isSuccessful() " + task.isSuccessful());
                    if (task.isSuccessful())
                        callback.loginSuccess();
                    else {
                        callback.loginFailure();
                    }
                }
            });
        } else {
            LogUtil.d(TAG, "validate error");
            callback.validateError("validate error");
        }
    }

    public void signInWithFacebook(String accessToken, final AuthInteractor callback) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            callback.loginSuccess();

                        } else {
                            Log.d(TAG, "signInWithCredential:failed:");
                            callback.loginFailure();
                        }
                    }
                });
    }

    public void signUp(String email, String password, final AuthInteractor callback) {
        if (Validator.validate(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUserRef.child(mAuth.getCurrentUser().getUid()).setValue(new User());
                                callback.signUpSuccess();
                            }
                            else{
                                callback.signUpFailure();
                            }
                        }
                    });
        } else {
            callback.validateError("validate error");
        }
    }

}
