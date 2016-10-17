package com.android.friendchat.main.contacts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.activity.ChatActivity;
import com.android.friendchat.view.adapter.FriendsAdapter;
import com.android.friendchat.view.adapter.FriendsAdapter.FriendAdapterClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {
    private static final String TAG = ContactsFragment.class.getSimpleName();

    @Bind(R.id.recycler_view)
    RecyclerView rvFriends;
    private DatabaseReference mUserRef;
    public ContactsFragment() {
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this,view);
        FriendsAdapter adapter = new FriendsAdapter(getActivity().getApplicationContext(),mUserRef);
        adapter.setListener(listener);
        rvFriends.setAdapter(adapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


    private FriendAdapterClickListener listener = new FriendAdapterClickListener(){


        @Override
        public void setId(String toId) {
            LogUtil.d(TAG,"toid "+toId);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("toId",toId);
            getActivity().startActivity(intent);
        }
    };
}
