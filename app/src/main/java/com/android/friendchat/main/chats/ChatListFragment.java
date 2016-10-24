package com.android.friendchat.main.chats;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.message.MessageInteractorImpl;
import com.android.friendchat.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends BaseFragment implements ChatListView {
    private static final String TAG = ChatListFragment.class.getSimpleName();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ChatListPresenter mPresenter;


    public ChatListFragment() {
        mPresenter = new ChatListPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.d(TAG,"onCreateView");
        Toast.makeText(getActivity(),"onCreateView",Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this,view);
        mPresenter.getChatList();;
        return view;
    }

    @Override
    public void renderMessage(ChatMessage message) {

    }
}
