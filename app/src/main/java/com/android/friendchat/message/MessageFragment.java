package com.android.friendchat.message;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.data.model.ChatMessage;
import com.android.friendchat.utils.FireBaseConst;
import com.android.friendchat.utils.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements MessagesView {

    private static final String TAG = MessageFragment.class.getSimpleName();
    private MessagePresenter mPresenter;
    @Bind(R.id.edt_chat_content)
    EditText edtChatContent;
    @Bind(R.id.rv_message)
    RecyclerView rvMessage;
    private MessagesAdapter mAdapter;
    private String toId;
    StorageReference mStorageRef;
    List<ChatMessage> messageList = new ArrayList<>();
    Uri photoURI;

    public MessageFragment() {
        mPresenter = new MessagePresenter(this);
    }

    public static MessageFragment newInstance(String toId) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("toId", toId);
        fragment.setArguments(bundle);
        LogUtil.d(TAG, "toId " + toId);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        toId = getArguments().getString("toId");
        mAdapter = new MessagesAdapter(getActivity(), messageList);
        rvMessage.setAdapter(mAdapter);
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getMessages(toId);
    }

    @OnClick(R.id.send_message)
    public void sendTextMessage() {
        mPresenter.senTextMessage(edtChatContent.getText().toString(), toId);
        edtChatContent.setText("");
    }

    @OnClick(R.id.attach)
    public void sendPhotoMessage() {
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FireBaseConst.STORAGE_URL);
        //pickImage();
       // pickImageFromCamera();
        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                 photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.android.friendchat.fileprovider",
                        photoFile);
                List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    getActivity().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (REQUEST_GALLERY_CODE == requestCode) {
                LogUtil.d(TAG, "onActivityResult");
                Uri uri = data.getData();
                mPresenter.senPhotoMessage(uri, toId);
            } else if (REQUEST_CAMERA_CODE == requestCode) {
                //Uri uri = data.getData();
                mPresenter.senPhotoMessage(photoURI, toId);
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                mPresenter.sen
            }
        }
    }


    @Override
    public void renderMessage(ChatMessage message) {
        mAdapter.addMessage(message);
        rvMessage.scrollToPosition(messageList.size() - 1);
    }

}
