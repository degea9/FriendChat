package com.android.friendchat.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.friendchat.R;
import com.android.friendchat.data.model.User;
import com.android.friendchat.view.adapter.FriendsAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView rvFriends;
    private DatabaseReference mUserRef;
    public FriendFragment() {
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this,view);
        FriendsAdapter adapter = new FriendsAdapter(getActivity().getApplicationContext(),mUserRef);
        rvFriends.setAdapter(adapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}
