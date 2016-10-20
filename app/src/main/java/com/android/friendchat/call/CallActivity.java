package com.android.friendchat.call;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.friendchat.R;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        addFragment();
    }

    private void addFragment() {
        String callerId = getIntent().getStringExtra("callerId");
        String receiverId = getIntent().getStringExtra("receiverId");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, CallFragment.newInstance(receiverId));
        transaction.commit();
    }
}
