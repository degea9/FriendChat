package com.android.friendchat.call;

/**
 * Created by hp 400 on 10/20/2016.
 */
public class CallPresenter {
    private CallView mView;
    private CallInteractor mInteractor;
    public CallPresenter(CallView view){
        mView = view;
        mInteractor = new CallInteractorImpl();
    }

    public void startCall(String receiverId,String sessionId){
        mInteractor.setup( receiverId, sessionId);
    }
}
