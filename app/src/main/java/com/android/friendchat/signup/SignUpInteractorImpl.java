package com.android.friendchat.signup;

import android.support.annotation.NonNull;

import com.android.friendchat.data.model.User;
import com.android.friendchat.data.validator.Validator;
import com.android.friendchat.utils.FireBaseConst;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by APC on 10/16/2016.
 */

public class SignUpInteractorImpl implements SignUpInteractor {
    private static final String TAG = SignUpInteractorImpl.class.getSimpleName();
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    public SignUpInteractorImpl() {
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void signUp(final String username, String email, String password, final OnFinishedListener callback) {
        if (Validator.validate(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = task.getResult().getUser().getUid();
                                User user = new User();
                                user.setUsername(username);
                                storeUser(uid,user, callback);
                            } else {
                                callback.signUpFailure("Email or password has been taken");
                            }
                        }
                    });
        } else {
            callback.validateError("validate error");
        }
    }

    private void storeUser(String uid,User user, final OnFinishedListener callback) {
        String userName = user.getUsername();
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("/"+ FireBaseConst.USER_TABLE+"/" + uid, user.toMap());
        userUpdates.put("/"+FireBaseConst.USERNAME_TABLE+"/" + userName, uid);
        mRootRef.updateChildren(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.signUpSuccess();
                } else {
                    callback.signUpFailure("Username has been taken");
                }
            }
        });
    }
}
