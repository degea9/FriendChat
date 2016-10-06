/**
 * Created by tuandang on 10/5/2016.
 */
package com.android.friendchat.interactor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.data.model.User;

public class ProfileInteractor {
    private DatabaseReference mUserRef;

    public ProfileInteractor(){
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }
    public void saveProfile(User user){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserRef.child(uid).setValue(user);
    }

    public void getProfile(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserRef.child(uid).or;
    }
}
