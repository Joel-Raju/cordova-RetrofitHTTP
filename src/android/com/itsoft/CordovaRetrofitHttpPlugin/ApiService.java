package com.itsoft;

import java.util.List;
import java.util.Map;

 
import retrofit2.Call;
import retrofit2.http.Url;
import retrofit2.http.HeaderMap;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.google.gson.JsonElement;

public interface ApiService {

	@GET()
    Call<JsonElement> getRequest(@Url String url, @HeaderMap Map<String, String> headers);

    @POST()
    Call<JsonElement> postRequest(@Url String url,  @HeaderMap Map<String, String> headers);
}