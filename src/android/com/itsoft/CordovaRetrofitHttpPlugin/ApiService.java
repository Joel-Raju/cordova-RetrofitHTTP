package com.itsoft;

import java.util.List;
import java.util.Map;

 
import retrofit2.Call;
import retrofit2.http.Url;
import retrofit2.http.HeaderMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

import okhttp3.ResponseBody;

import com.google.gson.JsonElement;

public interface ApiService {

	@GET()
    Call<JsonElement> getRequestWithDynamicUrl(@Url String url, @HeaderMap Map<String, String> headers);

    @POST()
    Call<JsonElement> postRequestWithDynamicUrl(@Url String url,  @HeaderMap Map<String, String> headers);

    @Streaming
    @GET()
    Call<ResponseBody> downloadFileWithDynamicUrl(@Url String url,  @HeaderMap Map<String, String> headers);
}