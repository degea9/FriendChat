package com.android.friendchat.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.friendchat.R;
import com.android.friendchat.data.model.Room;
import com.android.friendchat.presenter.RoomPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRoomFragment extends Fragment {
    private static final String TAG = CreateRoomFragment.class.getSimpleName();

    @Bind(R.id.name)
    EditText edtName;

    private RoomPresenter roomPresenter;
    Room mRoom = new Room();

    public CreateRoomFragment() {
        roomPresenter = new RoomPresenter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_room, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.thumbnail)
    public void uploadThumbnail() {

    }

    @OnClick(R.id.btnCreat)
    public void createRoom(){
        mRoom.setName(edtName.getText().toString().trim());
        roomPresenter.createRoom(mRoom);
    }

}
