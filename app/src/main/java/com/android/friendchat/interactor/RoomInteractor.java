package com.android.friendchat.interactor;

import com.android.friendchat.data.api.ApiClient;
import com.android.friendchat.data.api.SessionJson;
import com.android.friendchat.data.model.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.main.room.CreateRoomPresenter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp 400 on 10/12/2016.
 */
public class RoomInteractor {
    private DatabaseReference mRootRef;
    private CreateRoomPresenter mPresenter;

    public RoomInteractor(CreateRoomPresenter presenter) {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPresenter = presenter;
    }

    public void createRoom(final Room room){
        ApiClient.getClient().getSession(new Callback<SessionJson>() {
            @Override
            public void onResponse(Call<SessionJson> call, Response<SessionJson> response) {
                SessionJson sessionJson = response.body();
                room.setSessionId(sessionJson.getSessionId());
                mRootRef.child("room").push().setValue(room);
            }

            @Override
            public void onFailure(Call<SessionJson> call, Throwable t) {

            }
        });

    }

    public void senTextMessage(String message, String roomID) {
        ChatMessage textMessage = new ChatMessage();
        textMessage.setMessage(message);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId("");
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.textToMap());
        childUpdates.put("/room-message/"+roomID+"/"+ messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }

    public void senPhotoMessage(String url, String roomID) {
        ChatMessage textMessage = new ChatMessage();
        textMessage.setImageUrl(url);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textMessage.setFromId(uid);
        textMessage.setToId("");
        textMessage.setTimestamp(Calendar.getInstance().getTimeInMillis());
        String messageKey = mRootRef.child("message").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/message/" + messageKey, textMessage.photoToMap());
        childUpdates.put("/room-message/"+roomID+"/"+ messageKey, 1);
        mRootRef.updateChildren(childUpdates);
    }
}
