package com.android.friendchat.call;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.ButterKnife;

/**
 * Created by APC on 10/19/2016.
 */

public class CallFragment extends BaseFragment implements CallView {
    ImageView callerAvatar;

    public CallFragment() {
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
        return view;
    }

}


