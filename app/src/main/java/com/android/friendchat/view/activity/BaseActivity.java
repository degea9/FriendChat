package com.android.friendchat.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    protected void navigateTo(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    protected void showProgressDialog(String message){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void dissmissProgressDialog(){
       if(mProgressDialog!=null)mProgressDialog.dismiss();
    }
}
