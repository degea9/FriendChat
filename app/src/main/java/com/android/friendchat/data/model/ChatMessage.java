/**
 * Created by tuandang on 10/8/2016.
 */
package com.android.friendchat.data.model;

public class ChatMessage {
    private long timestamp;
    private String body;
    private Author author;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
