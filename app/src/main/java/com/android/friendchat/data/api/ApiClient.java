package com.android.friendchat.data.api;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp 400 on 10/11/2016.
 */
public class ApiClient {
    public static final String END_POINT = "https://degea9-opentok.herokuapp.com/";
    private static Retrofit retrofit = null;
    private static TokboxService service = null;

    private ApiClient(){
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(TokboxService.class);
        }
    }

    public static ApiClient getClient() {
       return new ApiClient();
    }

    public void getSession(Callback<SessionJson> callback){
        service.getSession().enqueue(callback);
    }
}
