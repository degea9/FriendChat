package com.android.friendchat.call;

import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by APC on 10/19/2016.
 */

public class CallFragment extends BaseFragment implements CallView {
    private CallPresenter mPresenter;
    ImageView callerAvatar;

    public CallFragment() {
        mPresenter = new CallPresenter(this);
    }

    public static CallFragment newInstance(String receiverId) {
        CallFragment fragment = new CallFragment();
        Bundle bundle = new Bundle();
        bundle.putString("receiverId", receiverId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        ButterKnife.bind(this, view);
        setupView();
        makeCall();
        return view;
    }

    private void setupView() {
    }

    private void makeCall() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String callerId = bundle.getString("callerId");
            String receiverId = bundle.getString("receiverId");
            String sessionId = bundle.getString("sessionId");
            mPresenter.startCall(receiverId, "1_MX40NTY5NTA3Mn5-MTQ3Njg5MzY5NDI0N35vV3JSTVgzR0gvZXlGT1c1bmpGTy9FT0R-fg");
        }
    }
}
