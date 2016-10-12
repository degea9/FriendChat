package com.android.friendchat.interactor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.presenter.RoomPresenter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp 400 on 10/12/2016.
 */
public class RoomInteractor {
    private DatabaseReference mRootRef;
    private RoomPresenter mPresenter;

    public RoomInteractor(RoomPresenter presenter) {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPresenter = presenter;
    }

    public void senTextMessage(String message, String roomID) {
        ChatMessage textMessage = new ChatMessage();
        textMessage.setMessage(message);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId("");
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.textToMap());
        childUpdates.put("/room-message/"+roomID+"/"+ messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }

    public void senPhotoMessage(String url, String roomID) {
        ChatMessage textMessage = new ChatMessage();
        textMessage.setImageUrl(url);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId("");
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.photoToMap());
        childUpdates.put("/room-message/"+roomID+"/"+ messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }
}
