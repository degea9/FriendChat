package com.android.friendchat.call;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.utils.FireBaseConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp 400 on 10/20/2016.
 */
public class CallInteractorImpl implements CallInteractor {
    private static final String TAG = CallInteractorImpl.class.getSimpleName();

    @Override
    public void notifyCall(String receiverId, String sessionId) {
        String callerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference callRef = FirebaseDatabase.getInstance().getReference(FireBaseConst.CALL_TABLE);
        Map<String, String> call = new HashMap<>();
        call.put("callerId", callerId);
        call.put("receiverId", receiverId);
        call.put("sessionId", sessionId);
        callRef.push().setValue(call);
    }


    @Override
    public void startCall() {

    }

    @Override
    public void endCall() {

    }

    @Override
    public void enableVideo() {

    }

    @Override
    public void disableVideo() {

    }
}
