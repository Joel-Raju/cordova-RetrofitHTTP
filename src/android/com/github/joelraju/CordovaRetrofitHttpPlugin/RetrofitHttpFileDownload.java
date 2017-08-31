/**
 * A HTTP plugin for Cordova / Phonegap
 */
package com.github.joelraju;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

import java.util.Map;

import org.apache.cordova.file.FileUtils;
import org.apache.cordova.CallbackContext;

import android.util.Log;

import retrofit2.Call; 
import retrofit2.Callback;
import retrofit2.Response;


import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import okhttp3.ResponseBody;

import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;
 
public class RetrofitHttpFileDownload extends RetrofitHttpRequest implements Runnable {
    
    private JSONObject responseObject = new JSONObject();
    private String filePath;

    public RetrofitHttpFileDownload(String urlString, Map<?, ?> params, Map<String, String> headers, CallbackContext callbackContext, String filePath) {
        super(urlString, params, headers, callbackContext);
        this.filePath = filePath;
    }
    
    @Override
    public void run() {

        ApiService apiService = ApiUtils.getService();

        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrl(this.getUrlString(), this.getHeaders());


        try {
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {

                            InputStream inputStream  = response.body().byteStream();
                            URI uri = new URI(filePath);
                            File file = new File(uri);
                        
                            if (!file.exists()) {
                                file.getParentFile().mkdirs();    
                                file.createNewFile();
                            }

                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            IOUtils.copy(inputStream, fileOutputStream);
                            
                            JSONObject fileEntry = FileUtils.getFilePlugin().getEntryForFile(file);
                            responseObject.put("file", fileEntry);
                
                            getCallbackContext().success(responseObject);

                        } else {
                            
                            responseObject.put("error", "There was an error downloading the file");
                            getCallbackContext().error(responseObject);
                        }
                    } catch (IOException e) {
                        System.out.println(Log.getStackTraceString(e));
                        respondWithError("IOException: "+ Log.getStackTraceString(e));
                    }catch (URISyntaxException e) {
                        respondWithError("URISyntaxException: "+ Log.getStackTraceString(e));
                    } catch (IllegalStateException e) {
                        respondWithError("IllegalStateException: "+ Log.getStackTraceString(e));
                    } catch (JSONException e) {
                        respondWithError("JSONException: "+ Log.getStackTraceString(e));
                    } catch (Exception e) {
                        System.out.println(Log.getStackTraceString(e));
                        respondWithError("Unknown Exception: "+ Log.getStackTraceString(e));
                    }
                }
     
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        responseObject.put("error", t.toString());
                        Log.e(TAG, t.toString());
                        getCallbackContext().error(responseObject);
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