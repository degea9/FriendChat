package com.android.friendchat.main.room;


import com.android.friendchat.base.BaseFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.R;
import com.android.friendchat.view.activity.RoomChatActivity;
import com.android.friendchat.view.adapter.RoomsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomListFragment extends BaseFragment {
    private static final String TAG = RoomListFragment.class.getSimpleName();
    private CreateRoomPresenter mPresenter;
    private DatabaseReference mReference;

    @Bind(R.id.fab_create_room)
    FloatingActionButton fab;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;


    public RoomListFragment() {
        //mPresenter = new CreateRoomPresenter();
        mReference = FirebaseDatabase.getInstance().getReference("rooms");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_room_list, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CreateRoomActivity.class);
                startActivity(intent);
            }
        });

        RoomsAdapter adapter = new RoomsAdapter(getActivity(),mReference);
        adapter.setListener(new RoomsAdapter.RoomAdapterClickListener() {
            @Override
            public void setId(String roomId,String sessionId) {
                Intent intent = new Intent(getActivity(), RoomChatActivity.class);
                intent.putExtra("roomId",roomId);
                intent.putExtra("sessionId",sessionId);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

}
