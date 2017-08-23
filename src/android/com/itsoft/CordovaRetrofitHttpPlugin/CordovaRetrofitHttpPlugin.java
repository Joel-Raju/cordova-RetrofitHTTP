package com.itsoft;


import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaRetrofitHttpPlugin extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("get")) {
            String urlString = args.getString(0);
            JSONObject params = args.getJSONObject(1);
            JSONObject headers = args.getJSONObject(2);
            HashMap<?, ?> paramsMap = this.getMapFromJSONObject(params);
            HashMap<String, String> headersMap = this.getStringMapFromJSONObject(headers);
            RetrofitHttpGet get = new RetrofitHttpGet(urlString, paramsMap, headersMap, callbackContext);
            cordova.getThreadPool().execute(get);
        } else if (action.equals("post")) {
            String urlString = args.getString(0);
            JSONObject params = args.getJSONObject(1);
            JSONObject headers = args.getJSONObject(2);
            HashMap<?, ?> paramsMap = this.getMapFromJSONObject(params);
            HashMap<String, String> headersMap = this.getStringMapFromJSONObject(headers);
            RetrofitHttpPost get = new RetrofitHttpPost(urlString, paramsMap, headersMap, callbackContext);
            cordova.getThreadPool().execute(get);
        } else if (action.equals("downloadFile")) {
            String urlString = args.getString(0);
            JSONObject params = args.getJSONObject(1);
            JSONObject headers = args.getJSONObject(2);
            HashMap<?, ?> paramsMap = this.getMapFromJSONObject(params);
            HashMap<String, String> headersMap = this.getStringMapFromJSONObject(headers);
            String filePath = args.getString(3);
            RetrofitHttpFileDownload download = new RetrofitHttpFileDownload(urlString, paramsMap, headersMap, callbackContext, filePath);
            cordova.getThreadPool().execute(download);
        } else if (action.equals("uploadFile")) {
            return false;
        }
        return true;
    }

    private HashMap<String, String> getStringMapFromJSONObject(JSONObject object) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<?> i = object.keys();

        while (i.hasNext()) {
            String key = (String)i.next();
            map.put(key, object.getString(key));
        }
        return map;
    }

    private HashMap<String, Object> getMapFromJSONObject(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Iterator<?> i = object.keys();

        while(i.hasNext()) {
            String key = (String)i.next();
            map.put(key, object.get(key));
        }
        return map;
    }
}
