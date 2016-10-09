/**
 * Created by tuandang on 10/8/2016.
 */
package com.android.friendchat.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.friendchat.R;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.LogUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class FriendsAdapter extends FirebaseRecyclerAdapter<User, FriendsAdapter.FriendsViewHolder>  {

    private static final String TAG = FriendsAdapter.class.getSimpleName();
    private Context mContext;

    public FriendsAdapter(Context context, DatabaseReference ref) {
        super(User.class, R.layout.item_friend, FriendsAdapter.FriendsViewHolder.class, ref);
        LogUtil.d(TAG, "FriendsAdapter() ");
        mContext = context;
    }

    @Override
    protected void populateViewHolder(FriendsViewHolder viewHolder, User model,final int position) {
        Picasso.with(mContext).load(model.getAvatar()).into(viewHolder.thumbnail);
        LogUtil.d(TAG, "model.getAvatar( " + model.getAvatar());
        LogUtil.d(TAG, "model.getFirstName() " + model.getFirstName());
        viewHolder.status.setText(model.getFirstName());
        if (mListener != null) {
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick1(position);
                }
            });
        }
    }

    public void setListener(FriendAdapterClickListener listener){
        mListener = listener;
    }

    public void onClick1(int position) {
        mListener.setId(getRef(position).getKey());
    }

    static class FriendsViewHolder extends RecyclerView.ViewHolder {
        //@Bind(R.id.user_status)
        TextView status;
        //@Bind(R.id.user_thumbnail)
        ImageView thumbnail;
        View mView;

        public FriendsViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            thumbnail = (ImageView) view.findViewById(R.id.user_thumbnail);
            status = (TextView) view.findViewById(R.id.user_status);
            mView = view;
        }
    }

    public FriendAdapterClickListener mListener;
    public interface FriendAdapterClickListener {
         void setId(String toId);
    }
}
