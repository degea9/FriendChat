package com.android.friendchat.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.friendchat.R;
import com.android.friendchat.data.model.TextMessage;
import com.android.friendchat.presenter.ChatPresenter;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.adapter.MessagesAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {

    private static final String TAG = ChatFragment.class.getSimpleName();
    private ChatPresenter mPresenter;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.edt_chat_content)
    EditText edtChatContent;
    @Bind(R.id.rv_message)
    RecyclerView rvMessage;
    private DatabaseReference mMessageRef;
    private DatabaseReference mUserMessageRef;
    private String toId;

    public ChatFragment() {
        mPresenter = new ChatPresenter();
        mMessageRef = FirebaseDatabase.getInstance().getReference().child("message");
        mUserMessageRef = FirebaseDatabase.getInstance().getReference().child("user-message");
    }

    public static ChatFragment newInstance(String toId){
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("toId",toId);
        fragment.setArguments(bundle);
        LogUtil.d(TAG,"toId "+toId);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        toId = getArguments().getString("toId");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mUserMessageRef.child(uid).child(toId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    LogUtil.d(TAG, "snapshot " + snapshot.getKey());
//                    mMessageRef.child(snapshot.getKey());
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        MessagesAdapter adapter = new MessagesAdapter(getActivity(),mUserMessageRef.child(uid).child(toId));
        rvMessage.setAdapter(adapter);
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @OnClick(R.id.btn_send)
    public void sendTextMessage(){
        mPresenter.senTextMessage(edtChatContent.getText().toString(),toId);
        edtChatContent.setText("");
    }

}
