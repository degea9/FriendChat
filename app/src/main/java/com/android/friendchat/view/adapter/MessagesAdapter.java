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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MessagesAdapter extends FirebaseRecyclerAdapter<Integer,MessagesAdapter.MessagesViewHolder> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private Context mContext;
    private DatabaseReference mMessageRef;

    public MessagesAdapter(Context context, DatabaseReference ref) {
        super(Integer.class, R.layout.item_chat, MessagesAdapter.MessagesViewHolder.class, ref);
        mContext = context;
        mMessageRef = FirebaseDatabase.getInstance().getReference().child("message");
    }
    @Override
    protected void populateViewHolder(final MessagesViewHolder viewHolder, Integer integer, int position) {
        String key = getRef(position).getKey();
        LogUtil.d(TAG, "populateViewHolder()position " + position + " message key " + key);
        mMessageRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LogUtil.d(TAG, "onDataChange " );
                TextMessage model = dataSnapshot.getValue(TextMessage.class);
                viewHolder.message.setText(model.getMessage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {
        //@Bind(R.id.user_status)
        TextView message;
        //@Bind(R.id.user_thumbnail)

        public MessagesViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            message = (TextView) view.findViewById(R.id.message);
        }
    }
}
