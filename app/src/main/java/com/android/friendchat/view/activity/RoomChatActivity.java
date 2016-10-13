package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.view.fragment.ChatFragment;
import com.android.friendchat.view.fragment.RoomChatFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class RoomChatActivity extends AppCompatActivity {
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);
        roomId = getIntent().getStringExtra("roomId");
        addFragment();
    }

    private void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, RoomChatFragment.newInstance(roomId));
        transaction.commit();
    }
}
