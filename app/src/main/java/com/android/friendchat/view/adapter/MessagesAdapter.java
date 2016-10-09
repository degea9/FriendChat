/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.friendchat.R;
import com.android.friendchat.data.model.TextMessage;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.LogUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class MessagesAdapter extends FirebaseRecyclerAdapter<TextMessage,MessagesAdapter.MessagesViewHolder> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private Context mContext;

    public MessagesAdapter(Context context, DatabaseReference ref) {
        super(TextMessage.class, R.layout.item_friend, MessagesAdapter.MessagesViewHolder.class, ref);
        LogUtil.d(TAG, "FriendsAdapter() ");
        mContext = context;
    }
    @Override
    protected void populateViewHolder(MessagesViewHolder viewHolder, TextMessage model, int position) {

    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {
        //@Bind(R.id.user_status)
        TextView status;
        //@Bind(R.id.user_thumbnail)
        ImageView thumbnail;
        View mView;

        public MessagesViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            thumbnail = (ImageView) view.findViewById(R.id.user_thumbnail);
            status = (TextView) view.findViewById(R.id.user_status);
            mView = view;
        }
    }
}
