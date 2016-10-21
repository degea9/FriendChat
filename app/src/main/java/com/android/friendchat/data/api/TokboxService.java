package com.android.friendchat.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hp 400 on 10/11/2016.
 */
public interface TokboxService {
    @GET("session")
    Call<SessionJson> getSession();

    @GET("session/mode/relay")
    Call<SessionJson> getRelaySession();

    @GET("generatetoken/{sessionId}")
    Call<SessionJson> generateToken(@Path("sessionId") String sessionId);
}
