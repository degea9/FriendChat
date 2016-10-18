package com.android.friendchat.message;

import com.google.firebase.auth.FirebaseAuth;

import com.android.friendchat.R;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.LogUtil;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp 400 on 10/18/2016.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private Context mContext;
    private List<ChatMessage> mMessages;

    static class MessagesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.contentWithBackground)
        LinearLayout contentWithBG;
        @Bind(R.id.message)
        TextView message;
        @Bind(R.id.message_date)
        TextView date;
        @Bind(R.id.photo)
        ImageView photo;

        public MessagesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public MessagesAdapter(Context mContext, List<ChatMessage> messages) {
        this.mContext = mContext;
        mMessages = messages;
    }


    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d(TAG, "onCreateViewHolder ");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);

        return new MessagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MessagesViewHolder viewHolder, int position) {
        LogUtil.d(TAG, "onBindViewHolder ");
        ChatMessage message = mMessages.get(position);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (!uid.equals(message.getFromId())) {
            viewHolder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) viewHolder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            viewHolder.contentWithBG.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) viewHolder.message.getLayoutParams();
            layoutParams1.gravity = Gravity.LEFT;
            viewHolder.message.setLayoutParams(layoutParams1);

            //viewHolder.message.setLayoutParams(params);
            viewHolder.date.setLayoutParams(layoutParams);
        } else {
            viewHolder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) viewHolder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            viewHolder.contentWithBG.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) viewHolder.message.getLayoutParams();
            layoutParams1.gravity = Gravity.RIGHT;
            viewHolder.message.setLayoutParams(layoutParams1);

            //viewHolder.message.setLayoutParams(params);
            viewHolder.date.setLayoutParams(layoutParams);
        }
        viewHolder.date.setText(message.getDate());
        if (message.getImageUrl() != null) {
            Picasso.with(mContext).load(message.getImageUrl()).resize(100, 100).into(viewHolder.photo);
            viewHolder.message.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.message.setText(message.getMessage());
        }

    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}


