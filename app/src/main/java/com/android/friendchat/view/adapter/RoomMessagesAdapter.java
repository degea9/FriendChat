/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.view.adapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.android.friendchat.R;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.LogUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RoomMessagesAdapter extends FirebaseRecyclerAdapter<Integer,RoomMessagesAdapter.MessagesViewHolder> {
    private static final String TAG = RoomMessagesAdapter.class.getSimpleName();
    private Context mContext;
    private DatabaseReference mMessageRef;
    private DatabaseReference mUserRef;

    public RoomMessagesAdapter(Context context, DatabaseReference ref) {
        super(Integer.class, R.layout.item_chat, RoomMessagesAdapter.MessagesViewHolder.class, ref);
        mContext = context;
        mMessageRef = FirebaseDatabase.getInstance().getReference().child("message");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
    }
    @Override
    protected void populateViewHolder(final MessagesViewHolder viewHolder, Integer integer, int position) {
        String key = getRef(position).getKey();
        LogUtil.d(TAG, "populateViewHolder()position " + position + " message key " + key);
        mMessageRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LogUtil.d(TAG, "onDataChange ");
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ChatMessage model = dataSnapshot.getValue(ChatMessage.class);
                if (!uid.equals(model.getFromId())) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.LEFT;
                    viewHolder.message.setLayoutParams(params);
                    viewHolder.date.setLayoutParams(params);
                    viewHolder.message.setBackgroundResource(R.drawable.out_message_bg);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT;
                    viewHolder.message.setLayoutParams(params);
                    viewHolder.date.setLayoutParams(params);
                    viewHolder.message.setBackgroundResource(R.drawable.in_message_bg);
                }
                viewHolder.date.setText(model.getDate());
                if(model.getImageUrl()!=null){
                    Picasso.with(mContext).load(model.getImageUrl()).resize(100,100).into(viewHolder.photo);
                    viewHolder.message.setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.message.setText(model.getMessage());
                }

                mUserRef.child(model.getFromId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        LogUtil.d(TAG, "avatar " + user.getAvatar());
                        viewHolder.username.setText(user.getFirstName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.username)
        TextView username;
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
}
