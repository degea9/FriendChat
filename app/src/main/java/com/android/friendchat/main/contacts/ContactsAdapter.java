/**
 * Created by tuandang on 10/8/2016.
 */
package com.android.friendchat.main.contacts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import com.amulyakhare.textdrawable.TextDrawable;
import com.android.friendchat.R;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.LogUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactsAdapter extends FirebaseRecyclerAdapter<User, ContactsAdapter.ContactsViewHolder> {

    private static final String TAG = ContactsAdapter.class.getSimpleName();
    private Context mContext;

    public ContactsAdapter(Context context, DatabaseReference ref) {
        super(User.class, R.layout.item_contact, ContactsAdapter.ContactsViewHolder.class, ref);
        LogUtil.d(TAG, "ContactsAdapter() ");
        mContext = context;
    }

    public ContactsAdapter(Context context, Query query) {
        super(User.class, R.layout.item_contact, ContactsAdapter.ContactsViewHolder.class, query);
        LogUtil.d(TAG, "ContactsAdapter() ");
        mContext = context;
    }

    @Override
    protected void populateViewHolder(ContactsViewHolder viewHolder, User model, final int position) {
        char title = model.getUsername().toUpperCase().charAt(0);
        viewHolder.username.setText(model.getUsername());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(120)  // width in px
                .height(120) // height in px
                .endConfig()
                .buildRound(Character.toString(title), Color.RED);
        if (model.getAvatar() != null)
            Picasso.with(mContext).load(model.getAvatar()).placeholder(drawable).into(viewHolder.thumbnail);
        else {
            viewHolder.thumbnail.setImageDrawable(drawable);
        }

        LogUtil.d(TAG, "model.getAvatar " + model.getAvatar());
        LogUtil.d(TAG, "model.getUsername " + model.getUsername());
        viewHolder.status.setText(model.getFirstName());
        if (mListener != null) {
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setId(getRef(position).getKey());
                }
            });

            viewHolder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.call(getRef(position).getKey());
                }
            });
        }
    }

    public void setListener(ContactsClickListener listener) {
        mListener = listener;
    }

//    public void onClick1(int position) {
//        mListener.setId(getRef(position).getKey());
//    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.username)
        TextView username;
        @Bind(R.id.user_status)
        TextView status;
        @Bind(R.id.user_thumbnail)
        ImageView thumbnail;
        @Bind(R.id.call)
        ImageView call;
        View mView;

        public ContactsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }

    public ContactsClickListener mListener;

    public interface ContactsClickListener {
        void setId(String toId);
        void call(String receiverId);
    }
}
