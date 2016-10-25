package com.android.friendchat.main.chats;

import android.widget.Toast;

import com.android.friendchat.FriendChatApplication;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by APC on 10/24/2016.
 */

public class ChatListInteractorImpl implements ChatListInteractor {
    private static final String TAG = ChatListInteractorImpl.class.getSimpleName();

    @Override
    public void getChatList(final OnFinishedListener callback) {
        final DatabaseReference mUserMessageRef = FirebaseDatabase.getInstance().getReference().child(FireBaseConst.USER_MESSAGE_TABLE);
        final DatabaseReference mMessageRef = FirebaseDatabase.getInstance().getReference().child(FireBaseConst.MESSAGE_TABLE);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserMessageRef.child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LogUtil.d(TAG, "onChildAdded " + dataSnapshot.getKey());
                mUserMessageRef.child(uid).child(dataSnapshot.getKey()).limitToLast(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        LogUtil.d(TAG, "onChildAddeddeeper " + dataSnapshot.getKey());
                        mMessageRef.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                LogUtil.d(TAG, "chatMessage " + chatMessage.getMessage());
                                callback.retrieveChatList(chatMessage);
                                //Toast.makeText(FriendChatApplication.get(),"onChildAdded "+chatMessage.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                        Toast.makeText(FriendChatApplication.get(), "onChildAdded " + chatMessage.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
