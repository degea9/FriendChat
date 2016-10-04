package com.android.friendchat.presenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.view.contract.LoginContract;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class LoginPresenter {
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
                    if (task.isSuccessful())
                        mContract.loginSuccess();
                    else{

                    }
                }
            });
        } else {
            mContract.loginFailure();
        }
    }
}
