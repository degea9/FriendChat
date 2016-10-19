package com.android.friendchat.base;


import android.app.Fragment;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class BaseFragment extends Fragment {
    protected static final int REQUEST_GALLERY_CODE = 1000;
    protected static final int REQUEST_CAMERA_CODE = 1001;
    protected static final int REQUEST_VIDEO_CAPTURE  = 1002;
    protected void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY_CODE);
    }

    protected void pickImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
        }
    }

    protected void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
}
