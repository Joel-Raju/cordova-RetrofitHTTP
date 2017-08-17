package com.CordovaRetrofitHttpPlugin;

import java.util.List;
import java.util.Map;

 
import retrofit2.Call;
import retrofit2.http.Url;
import retrofit2.http.HeaderMap;
import retrofit2.http.GET;
import retrofit2.http.POST;



import org.json.JSONException;
import org.json.JSONObject;


public interface ApiService {

	@GET()
    Call<JSONObject> getRequest(@Url String url, @HeaderMap Map<String, String> headers);

    @POST()
    Call<JSONObject> postRequest(@Url String url,  @HeaderMap Map<String, String> headers);
}