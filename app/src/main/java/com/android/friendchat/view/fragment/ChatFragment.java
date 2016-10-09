package com.android.friendchat.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.friendchat.R;
import com.android.friendchat.presenter.ChatPresenter;
import com.android.friendchat.utils.LogUtil;

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
    @Bind(R.id.rl_send)
    RecyclerView rvMessage;

    public ChatFragment() {
        mPresenter = new ChatPresenter();
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

    }

    @OnClick(R.id.btn_send)
    public void sendTextMessage(){
        mPresenter.senTextMessage(edtChatContent.getText().toString(),getArguments().getString("toId"));
        edtChatContent.setText("");
    }

}
