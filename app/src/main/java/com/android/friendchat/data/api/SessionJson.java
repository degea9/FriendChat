package com.android.friendchat.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hp 400 on 10/11/2016.
 */
public class SessionJson {
    @SerializedName("apiKey")
    String apiKey;
    @SerializedName("sessionId")
    String sessionId;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    String token;
}
