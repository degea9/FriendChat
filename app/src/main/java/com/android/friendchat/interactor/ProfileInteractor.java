/**
 * Created by tuandang on 10/5/2016.
 */
package com.android.friendchat.interactor;

import android.util.Log;

import com.android.friendchat.presenter.ProfilePresenter;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.activity.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.data.model.User;
import com.google.firebase.database.ValueEventListener;

public class ProfileInteractor {
    private static final String TAG = ProfileInteractor.class.getSimpleName();
    private DatabaseReference mUserRef;
    private ProfilePresenter mPresenter;
    public ProfileInteractor(){
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }

    public void setPresenter(ProfilePresenter presenter){
        mPresenter = presenter;
    }
    public void saveProfile(User user){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserRef.child(uid).setValue(user);
    }

    public void getProfile(){
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                 User user = dataSnapshot.getValue(User.class);
                mPresenter.displayProfile(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled ", databaseError.toException());
                // ...
            }
        };
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserRef.child(uid).addValueEventListener(userListener);
    }


}
