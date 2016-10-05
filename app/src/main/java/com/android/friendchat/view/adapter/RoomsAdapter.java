package com.android.friendchat.view.adapter;

import com.android.friendchat.R;
import com.android.friendchat.data.model.Room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp 400 on 10/5/2016.
 */
public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder> {

    private Context mContext;
    private List<Room> mRooms;

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.room_name)
        TextView name;
        @Bind(R.id.room_thumbnail)
        ImageView thumbnail;

        public RoomViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
        }
    }


    public RoomsAdapter(Context mContext, List<Room> roomList) {
        this.mContext = mContext;
        mRooms = roomList;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_card, parent, false);

        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RoomViewHolder holder, int position) {
        Room room = mRooms.get(position);
        holder.name.setText(room.getName());

    }


    @Override
    public int getItemCount() {
        return mRooms.size();
    }
}
