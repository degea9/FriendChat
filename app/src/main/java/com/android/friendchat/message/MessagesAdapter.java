/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.message;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.android.friendchat.R;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.FireBaseConst;
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

public class MessagesAdapter extends FirebaseRecyclerAdapter<Integer, MessagesAdapter.MessagesViewHolder> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private Context mContext;
    private DatabaseReference mMessageRef;

    public MessagesAdapter(Context context, DatabaseReference ref) {
        super(Integer.class, R.layout.item_chat, MessagesAdapter.MessagesViewHolder.class, ref);
        mContext = context;
        mMessageRef = FirebaseDatabase.getInstance().getReference().child(FireBaseConst.MESSAGE_TABLE);
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
                    viewHolder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);
                    LinearLayout.LayoutParams layoutParams =
                            (LinearLayout.LayoutParams) viewHolder.contentWithBG.getLayoutParams();
                    layoutParams.gravity = Gravity.LEFT;
                    viewHolder.contentWithBG.setLayoutParams(layoutParams);

                    LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)  viewHolder.message.getLayoutParams();
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

                    LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)  viewHolder.message.getLayoutParams();
                    layoutParams1.gravity = Gravity.RIGHT;
                    viewHolder.message.setLayoutParams(layoutParams1);

                    //viewHolder.message.setLayoutParams(params);
                    viewHolder.date.setLayoutParams(layoutParams);
                }
                viewHolder.date.setText(model.getDate());
                if (model.getImageUrl() != null) {
                    Picasso.with(mContext).load(model.getImageUrl()).resize(100, 100).into(viewHolder.photo);
                    viewHolder.message.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.message.setText(model.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

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
}