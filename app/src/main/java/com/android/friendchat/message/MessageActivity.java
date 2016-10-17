package com.android.friendchat.message;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;

public class MessageActivity extends BaseActivity {

    private String toId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toId = getIntent().getStringExtra("toId");
        addFragment();
    }

    private void addFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container, MessageFragment.newInstance(toId));
        transaction.commit();
    }
}
