package com.android.friendchat.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.friendchat.R;
import com.android.friendchat.view.fragment.ChatFragment;

public class ChatActivity extends BaseActivity {

    private String toId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toId = getIntent().getStringExtra("toId");
        addFragment();
    }

    private void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,ChatFragment.newInstance(toId));
        transaction.commit();
    }
}
