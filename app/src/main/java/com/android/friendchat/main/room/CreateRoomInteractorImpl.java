package com.android.friendchat.main.room;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.friendchat.data.api.ApiClient;
import com.android.friendchat.data.api.SessionJson;
import com.android.friendchat.data.model.Room;
import com.android.friendchat.utils.BitmapUtils;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by APC on 10/18/2016.
 */

public class CreateRoomInteractorImpl implements CreateRoomInteractor {
    private static final String TAG = CreateRoomInteractorImpl.class.getSimpleName();
    private DatabaseReference mRootRef;
    StorageReference mStorageRef;

    public CreateRoomInteractorImpl() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void createRoom(final String name, final String thumbnailUrl, final OnFinishedListener callback) {
        ApiClient.getClient().getSession(new Callback<SessionJson>() {
            @Override
            public void onResponse(Call<SessionJson> call, Response<SessionJson> response) {
                Room room = new Room();
                room.setName(name);
                room.setThumbnail(thumbnailUrl);
                SessionJson sessionJson = response.body();
                room.setSessionId(sessionJson.getSessionId());
                mRootRef.child(FireBaseConst.ROOM_TABLE).push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.createSuccess();
                        } else callback.createFailure();
                    }
                });
            }

            @Override
            public void onFailure(Call<SessionJson> call, Throwable t) {
                callback.createFailure();
            }
        });
    }

    @Override
    public void uploadThumbnail(Uri uri, final OnFinishedListener callback) {
        try {
            byte[] bitmapData = BitmapUtils.getBitmapData(uri, 300);
            mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FireBaseConst.STORAGE_URL);
            StorageReference filePath = mStorageRef.child(FireBaseConst.PHOTOS_FOLDER).child(uri.getLastPathSegment());
            filePath.putBytes(bitmapData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //mPresenter.senPhotoMessage(taskSnapshot.getDownloadUrl().toString(),roomId);
                    LogUtil.d(TAG, "upload success " + taskSnapshot.getDownloadUrl().toString());
                    callback.uploadThumbnailSuccess(taskSnapshot.getDownloadUrl().toString());
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
