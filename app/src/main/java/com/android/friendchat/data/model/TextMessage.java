/**
 * Created by tuandang on 10/8/2016.
 */
package com.android.friendchat.data.model;

import java.util.HashMap;
import java.util.Map;

public class TextMessage {
    private String message;

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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromId", fromId);
        result.put("toId", toId);
        result.put("message", message);
        result.put("timestamp", timestamp);
        return result;
    }

}
