package com.android.friendchat.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.android.friendchat.R;
import com.android.friendchat.view.adapter.GenderAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends BaseFragment {
    @Bind(R.id.profile_gender)
    Spinner mGender;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(view);
        setUpSpinner();
        return view;
    }

    private void setUpSpinner() {
        int[] flags = {R.drawable.ic_male,R.drawable.ic_female};
        String[] genders = getResources().getStringArray(R.array.genders);
        GenderAdapter genderAdapter=new GenderAdapter(getActivity().getApplicationContext(),flags,genders);
        mGender.setAdapter(genderAdapter);
    }

}
