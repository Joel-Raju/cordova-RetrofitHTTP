/**
 * A HTTP plugin for Cordova / Phonegap
 */
package com.github.joelraju;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.apache.cordova.CallbackContext;

import android.util.Log;

import retrofit2.Call; 
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import com.google.gson.JsonSyntaxException;

 
public class RetrofitHttpPost extends RetrofitHttpRequest implements Runnable {
    
    private JsonObject responseObject = new JsonObject();

    public RetrofitHttpPost(String urlString, Map<?, ?> params, Map<String, String> headers, CallbackContext callbackContext) {
        super(urlString, params, headers, callbackContext);
    }
    
    @Override
    public void run() {

        ApiService apiService = ApiUtils.getService();

        Call<JsonElement> call = apiService.postRequestWithDynamicUrl(this.getUrlString(), this.getHeaders());


        try {
            call.enqueue(new Callback<JsonElement>() {

                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            JsonElement responseElement = new JsonParser().parse(response.body().toString());
                            if (responseElement.isJsonObject()) {
                                responseObject.add("data", responseElement.getAsJsonObject());    
                                getCallbackContext().success(new JSONObject(responseObject.toString()));
                            } else if (responseElement.isJsonArray()) {
                                responseObject.add("data", responseElement.getAsJsonArray());
                                getCallbackContext().success(new JSONObject(responseObject.toString()));
                            }

                        } else {
                            responseObject.addProperty("error", "Null Response");
                            getCallbackContext().error(new JSONObject(responseObject.toString()));
                        }
                    } catch (JsonSyntaxException e) {
                        respondWithError("JsonSyntaxException: "+ Log.getStackTraceString(e));
                    } catch (IllegalStateException e) {
                        respondWithError("IllegalStateException: "+ Log.getStackTraceString(e));
                    } catch (JSONException e) {
                        respondWithError("JSONException: "+ Log.getStackTraceString(e));
                    } catch (Exception e) {
                        respondWithError("Unknown Exception: "+ Log.getStackTraceString(e));
                    }
                }
     
                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    try {
                        responseObject.addProperty("error", t.toString());
                        Log.e(TAG, t.toString());
                        getCallbackContext().error(new JSONObject(responseObject.toString()));
                    } catch (JSONException e) {
                        respondWithError("JSONException: "+ Log.getStackTraceString(e));
                    }
                }
            });
        } catch (NullPointerException e) {
            respondWithError("NullPointerException: "+ Log.getStackTraceString(e));
        } catch (Exception e) {
            respondWithError("Unknown Exception: "+ Log.getStackTraceString(e));
        }
        
    }
}