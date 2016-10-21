package com.android.friendchat.call;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.data.api.SessionJson;
import com.android.friendchat.utils.LogUtil;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallActivity extends BaseActivity implements CallView, Session.SessionListener, PublisherKit.PublisherListener, SubscriberKit.SubscriberListener {
    private static final String TAG = CallActivity.class.getSimpleName();
    private Session mSession;
    private SessionJson mSessionJson;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private CallPresenter mPresenter;
    private VideoCallFragment mVideoCallFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        ButterKnife.bind(this);
        mPresenter = new CallPresenter(this);
        getIntentData();
        stupCall();
        addFragment(new CallFragment());
    }

    private void getIntentData() {
        Intent data = getIntent();
        if (data != null) {
            String callerId = data.getStringExtra("callerId");
            String receiverId = data.getStringExtra("receiverId");
            mPresenter.startCall(receiverId, "1_MX40NTY5NTA3Mn5-MTQ3Njg5MzY5NDI0N35vV3JSTVgzR0gvZXlGT1c1bmpGTy9FT0R-fg");
            mSessionJson = (SessionJson) data.getSerializableExtra("sessionJson");
        }
    }

    private void addFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void stupCall() {
        initializeSession();
        initializePublisher();
    }

    private void initializeSession() {
        mSession = new Session(this, mSessionJson.getApiKey(), mSessionJson.getSessionId());
        mSession.setSessionListener(this);
        mSession.connect(mSessionJson.getToken());
    }

    /**
     * init publish stream
     */
    private void initializePublisher() {
        mPublisher = new Publisher(this);
        mPublisher.setPublisherListener(this);
        mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                BaseVideoRenderer.STYLE_VIDEO_FILL);
        mVideoCallFragment.addPublisherView(mPublisher.getView());
    }

    @OnClick(R.id.start_call)
    public void startCall() {
        addFragment(new VideoCallFragment());
    }

    @OnClick(R.id.start_call)
    public void endCall() {

    }

     /* Session Listener methods */

    /**
     * session is connected
     */
    @Override
    public void onConnected(Session session) {
        LogUtil.d(TAG, "Session Connected");
        if (mPublisher != null) {
            mSession.publish(mPublisher);
        }
    }

    /**
     * session is disconnected
     */
    @Override
    public void onDisconnected(Session session) {
        LogUtil.d(TAG, "Session onDisconnected");
    }

    /**
     * a stream is received when receiver is publishing stream
     */
    @Override
    public void onStreamReceived(Session session, Stream stream) {
        LogUtil.d(TAG, "Session onStreamReceived");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber(this, stream);
            mSubscriber.setSubscriberListener(this);
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                    BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSession.subscribe(mSubscriber);
        }
    }

    /**
     * receiver's stream is dropped
     */
    @Override
    public void onStreamDropped(Session session, Stream stream) {
        LogUtil.d(TAG, "Session onStreamDropped");
        if (mSubscriber != null) {
            mSubscriber = null;
            mVideoCallFragment.removeSubsriberView();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        LogUtil.d(TAG, "Session onError");
    }

    /* Publisher Listener methods */

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        LogUtil.d(TAG, "Publisher Stream Created");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        LogUtil.d(TAG, "Publisher Stream Destroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        LogUtil.d(TAG, "Publisher Stream onError");
    }

    /* Subscriber Listener methods */

    @Override
    public void onConnected(SubscriberKit subscriberKit) {
        LogUtil.d(TAG, "Subscriber Connected");

        mVideoCallFragment.addSubsriberView(mSubscriber.getView());
    }

    @Override
    public void onDisconnected(SubscriberKit subscriberKit) {
        LogUtil.d(TAG, "Subscriber Disconnected");
    }

    @Override
    public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
        LogUtil.d(TAG, "Subscriber onError");
    }
}
