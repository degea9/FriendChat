package com.android.friendchat.call;

/**
 * Created by hp 400 on 10/20/2016.
 */
public interface CallInteractor {
    void setup(String receiverId,String sessionId);
    void startCall();
    void endCall();
    void enableVideo();
    void disableVideo();
}
