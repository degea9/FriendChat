package com.android.friendchat.signin;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.utils.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by APC on 10/15/2016.
 */

public class SignInInteractorImpl implements SignInInteractor {
    private static final String TAG = SignInInteractorImpl.class.getSimpleName();
    private FirebaseAuth mAuth;

    public SignInInteractorImpl() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email, String password, final SignInInteractor.OnFinishedListener callback) {
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

    @Override
    public void signInWithFacebook(String accessToken, final SignInInteractor.OnFinishedListener callback) {
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

}
