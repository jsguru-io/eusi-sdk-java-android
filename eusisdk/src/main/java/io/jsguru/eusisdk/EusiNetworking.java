package io.jsguru.eusisdk;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Util class for networking operations
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

class EusiNetworking {

    static final String CONTENT_TYPE = "Content-Type";
    static final String CONTENT_TYPE_JSON = "application/json";
    static final String AUTHORIZATION = "Authorization";

    private final OkHttpClient okHttpClient = new OkHttpClient();

    EusiNetworking() {}

    String get(String url, Hashtable<String, String> headers, final Callback callback) throws IOException {
        // Create request
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        if(headers != null){
            // Set headers
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = (String) headers.get(key);
                requestBuilder.addHeader(key, value);
            }
        }

        if(callback == null){
            // Synchronous
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            String responseString = response.body().string();

            return responseString;
        } else {
            // Async
            okHttpClient.newCall(requestBuilder.build()).enqueue(callback);
            return null;
        }
    }

    String post(String url, Hashtable<String, String> headers, String requestBody, final Callback callback) throws IOException {
        // Create request
        MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, requestBody));

        if(headers != null){
            // Set headers
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = (String) headers.get(key);
                requestBuilder.addHeader(key, value);
            }
        }

        if(callback == null){
            // Synchronous
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            return response.body().string();
        } else {
            // Async
            okHttpClient.newCall(requestBuilder.build()).enqueue(callback);
            return null;
        }
    }

    static boolean haveError(String apiResponse) {
        if (apiResponse == null)
            return true;
        try {
            JSONObject object = new JSONObject(apiResponse);
            //TODO REMOVE CHECK FOR STATUS == 0 once api stops sending status on non error responses
            if (object.has("status") && object.getInt("status") != 0)
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    static int getErrorCode(String apiResponse) {
        try {
            JSONObject object = new JSONObject(apiResponse);
            return object.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static String getErrorMessage(String apiResponse) {
        try {
            JSONObject object = new JSONObject(apiResponse);
            return object.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "message";
    }

    static String getValidationMessage(String apiResponse){
        try {
            JSONObject object = new JSONObject(apiResponse);
            return object.getJSONArray("validation").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
