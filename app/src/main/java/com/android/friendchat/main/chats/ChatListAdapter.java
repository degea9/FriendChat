package com.android.friendchat.main.chats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.friendchat.R;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.LogUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by APC on 10/24/2016.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListAdapterViewHolder> {
    private static final String TAG = com.android.friendchat.message.MessagesAdapter.class.getSimpleName();
    private Context mContext;
    private List<ChatMessage> mMessages;

    static class ChatListAdapterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.contentWithBackground)
        LinearLayout contentWithBG;
        @Bind(R.id.message)
        TextView message;
        @Bind(R.id.message_date)
        TextView date;
        @Bind(R.id.photo)
        ImageView photo;
        @Bind(R.id.frame_container)
        FrameLayout mFrameLayout;
        @Bind(R.id.video)
        FensterVideoView video;
        @Bind(R.id.play_video_controller)
        MediaFensterPlayerController playerController;

        public ChatListAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public ChatListAdapter(Context mContext, List<ChatMessage> messages) {
        this.mContext = mContext;
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
            viewHolder.message.setVisibility(View.GONE);
            viewHolder.mFrameLayout.setVisibility(View.GONE);
            viewHolder.photo.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(message.getImageUrl()).resize(500, 500).centerInside().
                    into(viewHolder.photo);
        } else if (message.getVideoUrl() != null) {
            LogUtil.d(TAG, "onBindViewHolder video url " + message.getVideoUrl());
            viewHolder.message.setVisibility(View.GONE);
            viewHolder.photo.setVisibility(View.GONE);
            viewHolder.mFrameLayout.setVisibility(View.VISIBLE);
            viewHolder.video.setVideo(message.getVideoUrl());
            viewHolder.video.setMediaController(viewHolder.playerController);
            viewHolder.video.setVideo(message.getVideoUrl(),
                    MediaFensterPlayerController.DEFAULT_VIDEO_START);
            viewHolder.video.start();
        } else {
            viewHolder.photo.setVisibility(View.GONE);
            viewHolder.mFrameLayout.setVisibility(View.GONE);
            viewHolder.message.setVisibility(View.VISIBLE);
            viewHolder.message.setText(message.getMessage());
        }

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