package com.android.friendchat.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hp 400 on 10/11/2016.
 */
public interface TokboxService {
    @GET("session")
    Call<SessionJson> getSession();
}
