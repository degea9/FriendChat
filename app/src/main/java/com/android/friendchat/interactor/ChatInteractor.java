/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.interactor;

import com.android.friendchat.data.model.TextMessage;
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
        TextMessage textMessage = new TextMessage();
        textMessage.setMessage(message);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId(toId);
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.toMap());
        childUpdates.put("/user-message/" + uid + "/" + toId + "/" + messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }
}
