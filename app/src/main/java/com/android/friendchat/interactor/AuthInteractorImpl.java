package com.android.friendchat.interactor;

import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.presenter.AuthPresenter;
import com.android.friendchat.utils.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by APC on 10/15/2016.
 */

public class AuthInteractorImpl {
    private static final String TAG = AuthInteractorImpl.class.getSimpleName();
    private FirebaseAuth mAuth;

    public AuthInteractorImpl() {
        mAuth = FirebaseAuth.getInstance();
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


}
