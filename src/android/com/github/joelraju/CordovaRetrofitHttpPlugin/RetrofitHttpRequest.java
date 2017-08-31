/**
 * A HTTP plugin for Cordova / Phonegap
 */
package com.github.joelraju;



import org.apache.cordova.CallbackContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import java.util.Iterator;

import android.util.Log;
 
public abstract class RetrofitHttpRequest {

    protected static final String TAG = "CordovaRetrofitHTTP";
    
    protected static final String CHARSET = "UTF-8";

    private String urlString;
    private Map<?, ?> params;
    private Map<String, String> headers;
    private CallbackContext callbackContext;
    
    public RetrofitHttpRequest(String urlString, Map<?, ?> params, Map<String, String> headers, CallbackContext callbackContext) {
        this.urlString = urlString;
        this.params = params;
        this.headers = headers;
        this.callbackContext = callbackContext;
    }

    protected String getUrlString() {
        return this.urlString;
    }
    
    protected Map<?, ?> getParams() {
        return this.params;
    }
    
    protected Map<String, String> getHeaders() {
        return this.headers;
    }
    
    protected CallbackContext getCallbackContext() {
        return this.callbackContext;
    }
    
    protected void respondWithError(int status, String msg) {
        try {
            JSONObject response = new JSONObject();
            response.put("status", status);
            response.put("error", msg);
            this.callbackContext.error(response);
        } catch (JSONException e) {
            this.callbackContext.error(msg);
        }
    }
    
    protected void respondWithError(String msg) {
        this.respondWithError(500, msg);
    }
}
