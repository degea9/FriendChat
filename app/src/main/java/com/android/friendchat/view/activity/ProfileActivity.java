package com.android.friendchat.view.activity;

import com.android.friendchat.data.model.User;
import com.android.friendchat.utils.DatabaseUtils;
import com.android.friendchat.view.contract.ProfileContract;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.android.friendchat.R;
import com.android.friendchat.presenter.ProfilePresenter;
import com.android.friendchat.utils.FireBaseUtils;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.custom.MLRoundedImageView;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileContract {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    ProfilePresenter mPresenter;
    private static final int REQUEST_GALLERY_CODE = 1000;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.profile_avatar)
    MLRoundedImageView mAvatar;
    @Bind(R.id.profile_info)
    TextView mProfileInfo;
    StorageReference mStorageRef;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPresenter = new ProfilePresenter();
        mPresenter.setViewContract(this);
        mPresenter.getProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_profile) {
            navigateTo(EditProfileActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    mUserRef = FirebaseDatabase.getInstance().getReference().child("user");
                    User user = new User();
                    user.setAvatar(taskSnapshot.getDownloadUrl().toString());
                    mUserRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                    LogUtil.d(TAG, "upload success " + taskSnapshot.getDownloadUrl().toString());
                    Picasso.with(ProfileActivity.this).load(taskSnapshot.getDownloadUrl()).into(mAvatar);
                    Toast.makeText(ProfileActivity.this,"upload success",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void displayProfile(User user) {
        if(user!=null){
            mProfileInfo.setText(DatabaseUtils.getUserInfo(user));
        }
    }
}
