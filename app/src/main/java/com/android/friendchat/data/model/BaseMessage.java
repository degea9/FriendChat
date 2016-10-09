/**
 * Created by tuandang on 10/9/2016.
 */
package com.android.friendchat.data.model;

public class BaseMessage {
    private long timestamp;
    private String fromId;
    private String toId;

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
}
