package com.android.friendchat.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hp 400 on 10/4/2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected void navigateTo(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}
