/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.message;

import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.message.MessagePresenter;
import com.android.friendchat.utils.FireBaseConst;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessageInteractor {
    private DatabaseReference mRootRef;
    private MessagePresenter mPresenter;

    public MessageInteractor(MessagePresenter presenter) {
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
        childUpdates.put("/"+ FireBaseConst.MESSAGE_TABLE+"/" + messageKey, textMessage.textToMap());
        childUpdates.put("/"+ FireBaseConst.USER_MESSAGE_TABLE+"/" + uid + "/" + toId + "/" + messageKey, 1);
        childUpdates.put("/"+FireBaseConst.USER_MESSAGE_TABLE+"/" + toId + "/" + uid + "/" + messageKey, 1);
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
        childUpdates.put("/"+ FireBaseConst.MESSAGE_TABLE+"/"  + messageKey, textMessage.photoToMap());
        childUpdates.put("/"+ FireBaseConst.USER_MESSAGE_TABLE+"/" + uid + "/" + toId + "/" + messageKey, 1);
        childUpdates.put("/"+ FireBaseConst.USER_MESSAGE_TABLE+"/" + toId + "/" + uid + "/" + messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }
}
