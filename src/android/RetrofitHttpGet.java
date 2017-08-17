/**
 * A HTTP plugin for Cordova / Phonegap
 */
package com.CordovaRetrofitHttpPlugin;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import android.util.Log;

import retrofit2.Call; 
import retrofit2.Callback;
import retrofit2.Response;

import com.CordovaRetrofitHttpPlugin.ApiService;
import com.CordovaRetrofitHttpPlugin.RetrofitClient;

 
public class RetrofitHttpGet extends RetrofitHttpRequest implements Runnable {
    
    private JSONObject responseObject = new JSONObject();

    public RetrofitHttpGet(String urlString, Map<?, ?> params, Map<String, String> headers, CallbackContext callbackContext) {
        super(urlString, params, headers, callbackContext);
    }
    
    @Override
    public void run() {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<JSONObject> call = apiService.getRequest(this.getUrlString(), this.getHeaders());

        call.enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                try {
                    if (response.isSuccessful()) {
                        responseObject.put("data", new JSONObject(response.body().toString()));
                        getCallbackContext().success(responseObject);
                    } else {
                        responseObject.put("error", new JSONObject(response.body().toString()));
                        getCallbackContext().error(responseObject);
                    }
                } catch (JSONException e) {
                    respondWithError("JSONException: "+ Log.getStackTraceString(e));
                }
            }
 
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                try {
                    responseObject.put("error", t.toString());
                    Log.e(TAG, t.toString());
                    getCallbackContext().error(responseObject);
                } catch (JSONException e) {
                    respondWithError("JSONException: "+ Log.getStackTraceString(e));
                }
            }
        });
    }
}