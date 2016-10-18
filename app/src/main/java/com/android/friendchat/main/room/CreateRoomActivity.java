package com.android.friendchat.main.room;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.fragment.RoomChatFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRoomActivity extends BaseActivity implements CreateView {
    private static final String TAG = CreateRoomActivity.class.getSimpleName();
    @Bind(R.id.room_thumbnail)
    ImageView ivThumbnail;
    @Bind(R.id.room_name)
    EditText edtRoomName;
    private String thumbnail;

    private CreateRoomPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        ButterKnife.bind(this);
        mPresenter = new CreateRoomPresenter(this);
        setupView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create) {
            createRoom();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @OnClick(R.id.room_thumbnail)
    public void uploadThumnail() {
        pickImage();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_GALLERY_CODE == requestCode) {
            LogUtil.d(TAG, "onActivityResult");
            if (REQUEST_GALLERY_CODE == requestCode && resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                showProgressDialog(getString(R.string.uploading));
                mPresenter.uploadThumbnail(uri);
            }
        }
    }

    private void createRoom() {
        showProgressDialog(getString(R.string.creating_room));
        mPresenter.createRoom(edtRoomName.getText().toString(), thumbnail);
    }

    @Override
    public void createSuccess() {
        dissmissProgressDialog();
        finish();
    }

    @Override
    public void showFailureMessage() {
        dissmissProgressDialog();
        showMessageDialog(getString(R.string.create_failure));
    }

    @Override
    public void displayThumbnail(String url) {
        dissmissProgressDialog();
        thumbnail = url;
        Picasso.with(getApplicationContext()).load(url).resize(126,126).into(ivThumbnail);
    }
}
