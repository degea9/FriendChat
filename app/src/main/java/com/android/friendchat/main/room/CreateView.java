package com.android.friendchat.main.room;

/**
 * Created by APC on 10/18/2016.
 */

public interface CreateView {
    void createSuccess();
    void showFailureMessage();
    void displayThumbnail(String url);
}
