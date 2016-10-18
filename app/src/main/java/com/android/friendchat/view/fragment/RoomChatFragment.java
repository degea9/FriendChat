package com.android.friendchat.view.fragment;


import com.android.friendchat.base.BaseFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.android.friendchat.R;
import com.android.friendchat.main.room.CreateRoomPresenter;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.adapter.RoomMessagesAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomChatFragment extends BaseFragment {
    private static final String TAG = RoomChatFragment.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 1000;
    private CreateRoomPresenter mPresenter;

    @Bind(R.id.edt_chat_content)
    EditText edtChatContent;
    @Bind(R.id.rv_message)
    RecyclerView rvMessage;
    private String roomId ;
    StorageReference mStorageRef;
    private DatabaseReference mRoomMessageRef;
    public RoomChatFragment() {
        //mPresenter = new CreateRoomPresenter();
        //mRoomMessageRef = FirebaseDatabase.getInstance().getReference().child("room-message");

    }

    public static RoomChatFragment newInstance(String roomId){
        RoomChatFragment fragment = new RoomChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomId", roomId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_room_chat, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        roomId = getArguments().getString("roomId");
        RoomMessagesAdapter adapter = new RoomMessagesAdapter(getActivity(),mRoomMessageRef.child(roomId));
        rvMessage.setAdapter(adapter);
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @OnClick(R.id.btn_send)
    public void sendTextMessage(){
        mPresenter.senTextMessage(edtChatContent.getText().toString(), roomId);
        edtChatContent.setText("");
    }

    @OnClick(R.id.add_photo)
    public void sendPhotoMessage(){
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FireBaseConst.STORAGE_URL);
        pickImage();
    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_GALLERY_CODE==requestCode){
            LogUtil.d(TAG, "onActivityResult");
            if(REQUEST_GALLERY_CODE==requestCode){
                Uri uri = data.getData();

                StorageReference filePath = mStorageRef.child("Photos").child(uri.getLastPathSegment());
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mPresenter.senPhotoMessage(taskSnapshot.getDownloadUrl().toString(),roomId);
                        LogUtil.d(TAG, "upload success " + taskSnapshot.getDownloadUrl().toString());
                    }
                });
            }
        }
    }

}
