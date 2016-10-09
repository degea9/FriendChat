/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.data.model;

public class PhotoMessage extends BaseMessage {
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
}
