package com.android.friendchat.presenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.view.contract.SingUpContract;

import android.support.annotation.NonNull;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class SingUpPresenter extends BasePresenter{
    private SingUpContract mSingUpContract;
    private FirebaseAuth mAuth;

    public SingUpPresenter(SingUpContract singUpContract){
        mAuth = FirebaseAuth.getInstance();
        mSingUpContract = singUpContract;
    }

    /**
     * create user with email and password
     * @param email
     * @param password
     */
    public void signUp(String email,String password){
        if(Validator.validate(email,password)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mSingUpContract.navigateToLogin();
                        }
                    });
        }
        else{
            mSingUpContract.showError("");
        }
    }
}
