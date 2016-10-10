/**
 * Created by tuandang on 10/8/2016.
 */
package com.android.friendchat.data.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatMessage {
    private String message;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    private long timestamp;
    private String fromId;
    private String toId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> textToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromId", fromId);
        result.put("toId", toId);
        result.put("message", message);
        result.put("timestamp", timestamp);
        return result;
    }

    public Map<String, Object> photoToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromId", fromId);
        result.put("toId", toId);
        result.put("imageUrl", imageUrl);
        result.put("timestamp", timestamp);
        return result;
    }

    public Map<String, Object> videoToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromId", fromId);
        result.put("toId", toId);
        result.put("videoUrl", videoUrl);
        result.put("timestamp", timestamp);
        return result;
    }


    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return format.format(timestamp);
    }

}
