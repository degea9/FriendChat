package com.android.friendchat.call;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoCallFragment extends BaseFragment {
    @Bind(R.id.publisher_container)
     FrameLayout mPublisherViewContainer;
    @Bind(R.id.subscriber_container)
     FrameLayout mSubscriberViewContainer;


    public VideoCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_call, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    public void addPublisherView(View view){
        mPublisherViewContainer.addView(view);
    }

    public void addSubsriberView(View view){
        mSubscriberViewContainer.addView(view);
    }

    public void removePublisherView(){
        mPublisherViewContainer.removeAllViews();
    }

    public void removeSubsriberView(){
        mSubscriberViewContainer.removeAllViews();
    }
}
