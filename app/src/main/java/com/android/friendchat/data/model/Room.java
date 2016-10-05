package com.android.friendchat.data.model;

/**
 * Created by hp 400 on 10/5/2016.
 */
public class Room {
    private String name;
    private int joinedUsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int favoriteNumber;
    private int streamingNumber;

    public int getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(int joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public int getStreamingNumber() {
        return streamingNumber;
    }

    public void setStreamingNumber(int streamingNumber) {
        this.streamingNumber = streamingNumber;
    }
}
