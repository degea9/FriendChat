package com.android.friendchat.view.fragment;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.friendchat.R;
import com.android.friendchat.data.model.Room;
import com.android.friendchat.presenter.RoomPresenter;
import com.android.friendchat.view.adapter.RoomsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends BaseFragment {
    private static final String TAG = PopularFragment.class.getSimpleName();
    private RoomPresenter mPresenter;
    private DatabaseReference mReference;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;


    public PopularFragment() {
        mPresenter = new RoomPresenter();
        mReference = FirebaseDatabase.getInstance().getReference("room");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
//        Room room1 = new Room();
//        room1.setName("Room1");
//        room1.setSessionId("sessionID1");
//        room1.setThumbnail("https://firebasestorage.googleapis.com/v0/b/friendchat-260ce.appspot.com/o/Photos%2F93?alt=media&token=f9cb1b42-bcc5-4785-9701-6b7dd932180d");
//        Room room2 = new Room();
//        room2.setName("Room2");
//        room2.setSessionId("sessionID2");
//        room2.setThumbnail("https://firebasestorage.googleapis.com/v0/b/friendchat-260ce.appspot.com/o/Photos%2F93?alt=media&token=f9cb1b42-bcc5-4785-9701-6b7dd932180d");
//        mReference.push().setValue(room1);
//        mReference.push().setValue(room2);
        RoomsAdapter adapter = new RoomsAdapter(getActivity(),mReference);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

}
