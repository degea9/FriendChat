package com.android.friendchat.call;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.data.api.ApiClient;
import com.android.friendchat.data.api.SessionJson;
import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.FireBaseConst;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallActivity extends BaseActivity implements CallView, Session.SessionListener, PublisherKit.PublisherListener, SubscriberKit.SubscriberListener {
    private static final String TAG = CallActivity.class.getSimpleName();
    public static final String ACTION_OUTGOING_CALL = "com.android.friendchat.ACTION_OUTGOING_CALL";
    public static final String ACTION_INCOMING_CALL = "com.android.friendchat.ACTION_INCOMING_CALL";
    private Session mSession;
    private SessionJson mSessionJson;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private CallPresenter mPresenter;
    private VideoCallFragment mVideoCallFragment;
    private boolean isStartCalling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        ButterKnife.bind(this);
        mPresenter = new CallPresenter(this);
        getIntentData();
        //stupCall();
        addFragment(new CallFragment());
    }

    private void getIntentData() {
        Intent data = getIntent();
        if (data != null) {
            String callerId = data.getStringExtra("callerId");
            final String receiverId = data.getStringExtra("receiverId");
            mSessionJson = (SessionJson) data.getSerializableExtra("sessionJson");
            if (ACTION_OUTGOING_CALL.equals(data.getAction())) {
                LogUtil.d(TAG, "ACTION_OUTGOING_CALL");
                FirebaseDatabase.getInstance().getReference(FireBaseConst.USER_TABLE).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        LogUtil.d(TAG, "onDataChange");
                        User user = dataSnapshot.getValue(User.class);
                        ApiClient.getClient().getToken(user.getSessionId(), new Callback<SessionJson>() {
                            @Override
                            public void onResponse(Call<SessionJson> call, Response<SessionJson> response) {
                                LogUtil.d(TAG, "onResponse");
                                mSessionJson = response.body();
                                mPresenter.notifyCall(receiverId, mSessionJson.getSessionId());
                                //stupCall();
                            }

                            @Override
                            public void onFailure(Call<SessionJson> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //mPresenter.notifyCall(receiverId, "2_MX40NTY5NTA3Mn5-MTQ3NzAzODM3MjUzNX5neVEwc3hNeUpESTBNejV2M3NzVlptVDB-UH4");
            }

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
        if (isStartCalling)
            mVideoCallFragment.addPublisherView(mPublisher.getView());
    }

    @OnClick(R.id.start_call)
    public void startCall() {
        isStartCalling = true;
        LogUtil.d(TAG, "startCall");
        mVideoCallFragment = new VideoCallFragment();
        addFragment(mVideoCallFragment);
        Intent data = getIntent();
        stupCall();
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
        if (isStartCalling)
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
