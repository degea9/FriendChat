package com.android.friendchat.main;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.main.chats.ChatListFragment;
import com.android.friendchat.main.room.RoomListFragment;
import com.android.friendchat.view.adapter.RoomPagerAdapter;
import com.android.friendchat.view.fragment.CreateRoomFragment;
import com.android.friendchat.main.contacts.ContactsFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewpager)
    ViewPager mPager;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    RoomPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager();

        mTabs.setupWithViewPager(mPager);
    }

    private void setupViewPager() {
        mAdapter = new RoomPagerAdapter(getFragmentManager());
        mAdapter.addFragment(new ContactsFragment(), getString(R.string.popular));
       // mAdapter.addFragment(new VideoChatFragment(), "Video chat");
        mAdapter.addFragment(new RoomListFragment(), "Room");
        //mAdapter.addFragment(new RoomChatFragment(), "TestRoomChat");
        mAdapter.addFragment(new ChatListFragment(), "chat list");
        mPager.setAdapter(mAdapter);
    }


}
