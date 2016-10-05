package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.view.adapter.RoomPagerAdapter;
import com.android.friendchat.view.fragment.PopularFragment;

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
        RoomPagerAdapter adapter = new RoomPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PopularFragment(), getString(R.string.popular));
        adapter.addFragment(new PopularFragment(), "TWO");
        adapter.addFragment(new PopularFragment(), "THREE");
        mPager.setAdapter(adapter);
    }
}
