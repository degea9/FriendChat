package com.android.friendchat.base;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.friendchat.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class BaseActivity extends AppCompatActivity {
    private MaterialDialog mMessageDialog;
    private MaterialDialog mProgressDialog;
    protected void navigateTo(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    protected void showProgressDialog(String message){
        mProgressDialog =new MaterialDialog.Builder(this)
                .content(message)
                .widgetColorRes(R.color.pink)
                .theme(Theme.LIGHT)
                .progress(true, 0)
                .show();
        mProgressDialog.show();
    }

    protected void dissmissProgressDialog(){
       if(mProgressDialog!=null)mProgressDialog.dismiss();
    }

    protected void showMessageDialog(String message){
        mMessageDialog =new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .content(message)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.black)
                .show();
        mMessageDialog.show();
    }
}
