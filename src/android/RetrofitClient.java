package com.CordovaRetrofitHttpPlugin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
 
public class RetrofitClient {
 
    private static Retrofit retrofit = null;
 	private static final String BASE_URL = "http://www.example.com/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}