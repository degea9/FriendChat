package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.view.adapter.RoomPagerAdapter;
import com.android.friendchat.view.fragment.ChatFragment;
import com.android.friendchat.view.fragment.FriendFragment;
import com.android.friendchat.view.fragment.PopularFragment;
import com.android.friendchat.view.fragment.VideoChatFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RoomActivity extends BaseActivity {
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
        mAdapter = new RoomPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new FriendFragment(), getString(R.string.popular));
        mAdapter.addFragment(new VideoChatFragment(), "Video chat");
        //adapter.addFragment(new PopularFragment(), "THREE");
        mPager.setAdapter(mAdapter);
    }


}
