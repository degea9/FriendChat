package com.android.friendchat.main.chats;

import com.google.firebase.auth.FirebaseAuth;

import com.android.friendchat.R;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.DatabaseUtils;
import com.android.friendchat.utils.LogUtil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by APC on 10/24/2016.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListAdapterViewHolder> {
    private static final String TAG = com.android.friendchat.message.MessagesAdapter.class.getSimpleName();
    private List<ChatMessage> mMessages;

    static class ChatListAdapterViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.timestamp)
        TextView timestamp;
        @Bind(R.id.username)
        TextView username;
        @Bind(R.id.message)
        TextView message;
        @Bind(R.id.thumbnail)
        ImageView thumbnail;

        public ChatListAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public ChatListAdapter(List<ChatMessage> messages) {
        mMessages = messages;
    }


    @Override
    public ChatListAdapter.ChatListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d(TAG, "onCreateViewHolder ");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_list, parent, false);

        return new ChatListAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatListAdapter.ChatListAdapterViewHolder viewHolder, int position) {
        LogUtil.d(TAG, "onBindViewHolder ");
        ChatMessage message = mMessages.get(position);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (message.getMessage() != null) {
            viewHolder.message.setText(message.getMessage());
        } else {
            viewHolder.message.setText("send you image");
        }

        viewHolder.timestamp.setText(message.getDate());
    }

    /**
     * add message
     *
     * @param message: new incoming message
     */
    public void addMessage(ChatMessage message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}