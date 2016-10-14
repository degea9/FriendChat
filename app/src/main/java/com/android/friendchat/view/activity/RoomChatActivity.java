package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.view.fragment.ChatFragment;
import com.android.friendchat.view.fragment.RoomChatFragment;
import com.android.friendchat.view.fragment.VideoChatFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class RoomChatActivity extends AppCompatActivity {
    private String roomId;
    private String sessionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);
        roomId = getIntent().getStringExtra("roomId");
        sessionId = getIntent().getStringExtra("sessionId");
        addFragment();
    }

    private void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.chat_container, RoomChatFragment.newInstance(roomId));
        transaction.add(R.id.video_container, VideoChatFragment.newInstance(sessionId));
        transaction.commit();
    }
}
