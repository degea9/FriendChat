package com.android.friendchat.main.contacts;


import com.android.friendchat.call.CallActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.message.MessageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {
    private static final String TAG = ContactsFragment.class.getSimpleName();

    @Bind(R.id.recycler_view)
    RecyclerView rvFriends;
    private DatabaseReference mUserRef;
    public ContactsFragment() {
        mUserRef = FirebaseDatabase.getInstance().getReference().child(FireBaseConst.USER_TABLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this,view);
        ContactsAdapter adapter = new ContactsAdapter(getActivity().getApplicationContext(),mUserRef.orderByChild("username"));
        adapter.setListener(listener);
        rvFriends.setAdapter(adapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


    private ContactsAdapter.ContactsClickListener listener = new ContactsAdapter.ContactsClickListener(){

        @Override
        public void setId(String toId) {
            LogUtil.d(TAG,"toid "+toId);
            Intent intent = new Intent(getActivity(), MessageActivity.class);
            intent.putExtra("toId",toId);
            getActivity().startActivity(intent);
        }

        @Override
        public void call(String receiverId) {
            Intent intent = new Intent(getActivity(), CallActivity.class);
            intent.setAction(CallActivity.ACTION_OUTGOING_CALL);
            intent.putExtra("receiverId",receiverId);
            startActivity(intent);
        }
    };
}
