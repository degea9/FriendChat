package com.android.friendchat.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.android.friendchat.call.CallActivity;
import com.android.friendchat.utils.LogUtil;

import android.content.Intent;

import java.util.Map;

/**
 * Created by hp 400 on 10/20/2016.
 */
public class CallNotificationService extends FirebaseMessagingService {
    private static final String TAG = CallNotificationService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        LogUtil.d(TAG, "onMessageReceived: " );
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        LogUtil.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogUtil.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String,String> data = remoteMessage.getData();
            String callerId = data.get("callerId");
            String sessionId = data.get("sessionId");
            notifyInCommingCall(callerId,sessionId);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogUtil.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void notifyInCommingCall(String callerId,String sessionId){
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra("callerId",callerId);
        intent.putExtra("sessionId",sessionId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
