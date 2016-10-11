package com.android.friendchat.view.adapter;

import com.android.friendchat.R;
import com.android.friendchat.utils.LogUtil;
import com.opentok.android.Subscriber;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp 400 on 10/11/2016.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {
    private static final String TAG = VideosAdapter.class.getSimpleName();
    private Context mContext;
    private List<Subscriber> mSubscribers;

    static class VideosViewHolder extends RecyclerView.ViewHolder {

        //@Bind(R.id.video_container)
        FrameLayout videoContainer;

        public VideosViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            videoContainer = (FrameLayout)view.findViewById(R.id.video_container);
        }
    }


    public VideosAdapter(Context mContext, List<Subscriber> subscribers) {
        this.mContext = mContext;
        mSubscribers = subscribers;
    }

    public void addSubsriber(Subscriber subscriber){
        LogUtil.d(TAG,"addSubsriber");
        mSubscribers.add(subscriber);
        notifyDataSetChanged();
    }

    public void removeDataAt(int position ){
        LogUtil.d(TAG,"addSubsriber");
        mSubscribers.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);

        return new VideosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideosViewHolder holder, int position) {
        Subscriber subscriber = mSubscribers.get(position);

        holder.videoContainer.addView(subscriber.getView());

    }


    @Override
    public int getItemCount() {
        return mSubscribers.size();
    }
}

