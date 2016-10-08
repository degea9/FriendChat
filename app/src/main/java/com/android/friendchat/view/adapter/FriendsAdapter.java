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
import com.android.friendchat.data.model.Room;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.LogUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendsAdapter extends FirebaseRecyclerAdapter<User, FriendsAdapter.FriendsViewHolder> {

    private static final String TAG = FriendsAdapter.class.getSimpleName();
    private Context mContext;

    public FriendsAdapter(Context context, DatabaseReference ref) {
        super(User.class, R.layout.item_friend, FriendsAdapter.FriendsViewHolder.class, ref);
        LogUtil.d(TAG,"FriendsAdapter() ");
        mContext = context;
    }

    @Override
    protected void populateViewHolder(FriendsViewHolder viewHolder, User model, int position) {
        Picasso.with(mContext).load(model.getAvatar()).into(viewHolder.thumbnail);
        LogUtil.d(TAG,"model.getAvatar( "+model.getAvatar());
        LogUtil.d(TAG,"model.getFirstName() "+model.getFirstName());
        viewHolder.status.setText(model.getFirstName());
    }

    static class FriendsViewHolder extends RecyclerView.ViewHolder {
        //@Bind(R.id.user_status)
        TextView status;
        //@Bind(R.id.user_thumbnail)
        ImageView thumbnail;

        public FriendsViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            thumbnail = (ImageView)view.findViewById(R.id.user_thumbnail);
            status = (TextView)view.findViewById(R.id.user_status);
        }
    }
}
