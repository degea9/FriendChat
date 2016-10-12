package com.android.friendchat.view.fragment;


import com.android.friendchat.R;
import com.android.friendchat.data.api.ApiClient;
import com.android.friendchat.data.api.SessionJson;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.adapter.VideosAdapter;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoChatFragment extends Fragment implements Session.SessionListener, PublisherKit.PublisherListener, SubscriberKit.SubscriberListener {
    private static final String TAG = VideoChatFragment.class.getSimpleName();
    private Session mSession;
    private Publisher mPublisher;
    @Bind(R.id.videos_view)
     RecyclerView mVideosView;
    private VideosAdapter mVideosAdapter;
    private List<Subscriber> mSubscribers = new ArrayList<>();
    @Bind(R.id.publisher_container)
    FrameLayout mPublisherViewContainer;

    public VideoChatFragment() {
        // Required empty public constructor
    }

    public static VideoChatFragment newInstance(){
        VideoChatFragment fragment = new VideoChatFragment();
        Bundle bundle = new Bundle();
        //bundle.putString("sessionId",)
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_chat, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        getOpentokSession();
        return view;

    }

    private void initRecyclerView() {
        mVideosAdapter = new VideosAdapter(getActivity(),mSubscribers);
        mVideosView.setAdapter(mVideosAdapter);
        mVideosView.setLayoutManager(new GridLayoutManager(getActivity(),3));
    }

    private void getOpentokSession() {
        ApiClient.getClient().getSession(mCallback);
    }

    private void initializeSession(SessionJson sessionJson) {
        mSession = new Session(getActivity(), sessionJson.getApiKey(), sessionJson.getSessionId());
        mSession.setSessionListener(this);
        mSession.connect(sessionJson.getToken());
    }

    private void initializePublisher() {
        mPublisher = new Publisher(getActivity());
        mPublisher.setPublisherListener(this);
        mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                BaseVideoRenderer.STYLE_VIDEO_FILL);
        mPublisherViewContainer.addView(mPublisher.getView());
    }

    private Callback<SessionJson> mCallback = new Callback<SessionJson>() {
        @Override
        public void onResponse(Call<SessionJson> call, Response<SessionJson> response) {
            SessionJson sessionJson = response.body();
            LogUtil.d(TAG, "session " + sessionJson.getSessionId());
            initializeSession(sessionJson);
            initializePublisher();
        }

        @Override
        public void onFailure(Call<SessionJson> call, Throwable t) {

        }
    };

    @Override
    public void onConnected(Session session) {
        LogUtil.d(TAG, "Session Connected " + session.getConnection().getConnectionId());
        if (mPublisher != null) {
            mSession.publish(mPublisher);
        }
    }

    @Override
    public void onDisconnected(Session session) {
        LogUtil.d(TAG, "Session onDisconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        LogUtil.d(TAG, "Session onStreamReceived stream.getName() " + stream.getName() + " stream.getStreamId() " + stream.getStreamId());
        LogUtil.d(TAG, "session connectionId " + session.getConnection().getConnectionId());
        LogUtil.d(TAG, "stream connectionId " + stream.getConnection().getConnectionId());
        Subscriber subscriber = new Subscriber(getActivity(), stream);
        subscriber.setSubscriberListener(this);
        subscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                BaseVideoRenderer.STYLE_VIDEO_FILL);
        mSubscribers.add(subscriber);
        mSession.subscribe(subscriber);

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        LogUtil.d(TAG, "Session onStreamDropped");
        for(int i=0;i<mSubscribers.size();i++ ){
            Subscriber subscriber = mSubscribers.get(i);
            if(subscriber.getStream().getConnection().getConnectionId()==stream.getConnection().getConnectionId()){
                mVideosView.removeViewAt(i);
                mVideosAdapter.removeDataAt(i);
                mVideosAdapter.notifyItemRemoved(i);
                mVideosAdapter.notifyItemRangeChanged(i,mSubscribers.size());
            }
        }
//        if (mSubscriber != null) {
//            mSubscriber = null;
//            mSubscriberViewContainer.removeAllViews();
//            mSubscriberViewContainer2.removeAllViews();
//        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        LogUtil.d(TAG, "Session onError");
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        LogUtil.d(TAG, "PublisherKit onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        LogUtil.d(TAG, "PublisherKit onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        LogUtil.d(TAG, "PublisherKit onError");
    }

    @Override
    public void onConnected(SubscriberKit subscriberKit) {
        LogUtil.d(TAG, "SubscriberKit onConnected "+subscriberKit.getStream().getConnection().getConnectionId());
        mVideosAdapter.notifyDataSetChanged();

        //if (mSubscriber != null) mSubscriberViewContainer.addView(mSubscriber.getView());
        //if (mSubscriber2 != null) mSubscriberViewContainer2.addView(mSubscriber2.getView());
    }

    @Override
    public void onDisconnected(SubscriberKit subscriberKit) {
        LogUtil.d(TAG, "SubscriberKit onDisconnected");
    }

    @Override
    public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
        LogUtil.d(TAG, "SubscriberKit onError");
    }
}
