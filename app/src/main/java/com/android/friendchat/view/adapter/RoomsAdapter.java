/**
 * Created by tuandang on 10/8/2016.
 */
package com.android.friendchat.view.adapter;

import com.google.firebase.database.DatabaseReference;

import com.android.friendchat.R;
import com.android.friendchat.data.model.Room;
import com.android.friendchat.utils.LogUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RoomsAdapter extends FirebaseRecyclerAdapter<Room, RoomsAdapter.RoomsViewHolder>  {

    private static final String TAG = RoomsAdapter.class.getSimpleName();
    private Context mContext;

    public RoomsAdapter(Context context, DatabaseReference ref) {
        super(Room.class, R.layout.item_room, RoomsAdapter.RoomsViewHolder.class, ref);
        LogUtil.d(TAG, "FriendsAdapter() ");
        mContext = context;
    }

    @Override
    protected void populateViewHolder(RoomsViewHolder viewHolder, Room model,final int position) {
        Picasso.with(mContext).load(model.getThumbnail()).into(viewHolder.thumbnail);
        viewHolder.name.setText(model.getName());
        LogUtil.d(TAG, "model.getName() " + model.getName());
        if (mListener != null) {
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setId(getRef(position).getKey());
                }
            });
        }
    }

    public void setListener(RoomAdapterClickListener listener){
        mListener = listener;
    }

    static class RoomsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.room_name)
        TextView name;
        @Bind(R.id.room_thumbnail)
        ImageView thumbnail;
        View mView;

        public RoomsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }

    public RoomAdapterClickListener mListener;
    public interface RoomAdapterClickListener {
        void setId(String roomId);
    }
}
