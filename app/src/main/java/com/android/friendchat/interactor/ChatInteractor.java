/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.interactor;

import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.presenter.ChatPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatInteractor {
    private DatabaseReference mRootRef;
    private ChatPresenter mPresenter;

    public ChatInteractor(ChatPresenter presenter) {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPresenter = presenter;
    }

    public void senTextMessage(String message, String toId) {
        ChatMessage textMessage = new ChatMessage();
        textMessage.setMessage(message);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId(toId);
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.textToMap());
        childUpdates.put("/user-message/" + uid + "/" + toId + "/" + messageKey, 1);
        childUpdates.put("/user-message/" + toId + "/" + uid + "/" + messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }

    public void senPhotoMessage(String url, String toId) {
        ChatMessage textMessage = new ChatMessage();
        textMessage.setImageUrl(url);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId(toId);
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.photoToMap());
        childUpdates.put("/user-message/" + uid + "/" + toId + "/" + messageKey, 1);
        childUpdates.put("/user-message/" + toId + "/" + uid + "/" + messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }
}
