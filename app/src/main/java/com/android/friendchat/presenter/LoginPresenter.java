package com.android.friendchat.presenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.contract.LoginContract;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private FirebaseAuth mAuth;
    private LoginContract mContract;

    public LoginPresenter(LoginContract loginContract) {
        mAuth = FirebaseAuth.getInstance();
        mContract = loginContract;
    }

    public void login(String email, String password) {
        if (Validator.validate(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    LogUtil.d(TAG,"task.isSuccessful() "+task.isSuccessful());
                    if (task.isSuccessful())
                        mContract.loginSuccess();
                    else{
                        mContract.loginFailure();
                    }
                }
            });
        } else {
            LogUtil.d(TAG,"login failure");
            mContract.loginFailure();
        }
    }
}
