package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.presenter.ProfilePresenter;
import com.android.friendchat.utils.FireBaseUtils;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.custom.MLRoundedImageView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    ProfilePresenter mPresenter;
    private static final int REQUEST_GALLERY_CODE = 1000;
    @Bind(R.id.profile_avatar)
    CircleImageView mAvatar;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.profile_avatar)
    public void uploadImage(){
        showProgressDialog(getString(R.string.uploading));
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FireBaseUtils.STORAGE_URL);
        pickImage();
    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_GALLERY_CODE==requestCode){
            Uri uri = data.getData();

            StorageReference filePath = mStorageRef.child("Photos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dissmissProgressDialog();
                    LogUtil.d(TAG,"upload success "+taskSnapshot.getDownloadUrl());
                    Picasso.with(ProfileActivity.this).load(taskSnapshot.getDownloadUrl()).into(mAvatar);
                    Toast.makeText(ProfileActivity.this,"upload success",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
