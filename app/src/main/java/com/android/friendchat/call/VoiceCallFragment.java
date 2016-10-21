package com.android.friendchat.call;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoiceCallFragment extends BaseFragment {


    public VoiceCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_call, container, false);
    }

}
