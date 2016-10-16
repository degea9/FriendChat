package com.android.friendchat.signup;

import android.support.annotation.NonNull;

import com.android.friendchat.data.model.User;
import com.android.friendchat.data.validator.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by APC on 10/16/2016.
 */

public class SignUpInteractorImpl implements SignUpInteractor{
    private static final String TAG = SignUpInteractorImpl.class.getSimpleName();
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    public SignUpInteractorImpl() {
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }

    @Override
    public void signUp(String email, String password,final OnFinishedListener callback) {
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
