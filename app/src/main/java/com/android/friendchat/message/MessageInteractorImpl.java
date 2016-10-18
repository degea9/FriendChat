/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.message;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessageInteractorImpl implements MessageInteractor {
    private static final String TAG = MessageInteractorImpl.class.getSimpleName();
    private DatabaseReference mRootRef;
    private MessagePresenter mPresenter;

    public MessageInteractorImpl(MessagePresenter presenter) {
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
        childUpdates.put("/" + FireBaseConst.MESSAGE_TABLE + "/" + messageKey, textMessage.textToMap());
        childUpdates.put("/" + FireBaseConst.USER_MESSAGE_TABLE + "/" + uid + "/" + toId + "/" + messageKey, 1);
        childUpdates.put("/" + FireBaseConst.USER_MESSAGE_TABLE + "/" + toId + "/" + uid + "/" + messageKey, 1);
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
        childUpdates.put("/" + FireBaseConst.MESSAGE_TABLE + "/" + messageKey, textMessage.photoToMap());
        childUpdates.put("/" + FireBaseConst.USER_MESSAGE_TABLE + "/" + uid + "/" + toId + "/" + messageKey, 1);
        childUpdates.put("/" + FireBaseConst.USER_MESSAGE_TABLE + "/" + toId + "/" + uid + "/" + messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }

    @Override
    public void getMessages(String toId,final MessageInteractor.OnFinishedListener callback) {
        DatabaseReference mUserMessageRef = FirebaseDatabase.getInstance().getReference().child(FireBaseConst.USER_MESSAGE_TABLE);
        final DatabaseReference mMessageRef = FirebaseDatabase.getInstance().getReference().child(FireBaseConst.MESSAGE_TABLE);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserMessageRef.child(uid).child(toId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                LogUtil.d(TAG, "onChildAdded");
                String key = dataSnapshot.getKey();
                mMessageRef.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                        callback.retrieveMessage(message);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                LogUtil.d(TAG, "onChildChanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LogUtil.d(TAG, "onChildRemoved");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                LogUtil.d(TAG, "onChildMoved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.d(TAG, "onCancelled");
            }
        });
    }

}