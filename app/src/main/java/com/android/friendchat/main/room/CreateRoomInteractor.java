package com.android.friendchat.main.room;

import android.net.Uri;

/**
 * Created by APC on 10/18/2016.
 */

public interface CreateRoomInteractor {
    interface OnFinishedListener{
        void createSuccess();
        void createFailure();
        void uploadThumbnailSuccess(String returnedUrl);
    }
    void createRoom(String name,String thumbnailUrl,OnFinishedListener callback );
    void uploadThumbnail(Uri uri,OnFinishedListener callback);
}
