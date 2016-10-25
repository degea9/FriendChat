package com.android.friendchat.main.chats;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.amulyakhare.textdrawable.TextDrawable;
import com.android.friendchat.FriendChatApplication;
import com.android.friendchat.R;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;
import com.squareup.picasso.Picasso;

import android.graphics.Color;
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
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FireBaseConst.USER_TABLE);


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
        ref.child(message.getToId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                viewHolder.username.setText(user.getUsername());
                char title = user.getUsername().toUpperCase().charAt(0);
                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .width(120)  // width in px
                        .height(120) // height in px
                        .endConfig()
                        .buildRound(Character.toString(title), Color.RED);
                if (user.getAvatar() != null)
                    Picasso.with(FriendChatApplication.get()).load(user.getAvatar()).into(viewHolder.thumbnail);
                else {
                    viewHolder.thumbnail.setImageDrawable(drawable);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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