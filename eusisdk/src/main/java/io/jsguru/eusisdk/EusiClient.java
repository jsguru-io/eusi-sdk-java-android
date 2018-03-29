package io.jsguru.eusisdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Hashtable;

import io.jsguru.eusisdk.callbacks.EusiAuthorizeCallback;
import io.jsguru.eusisdk.callbacks.EusiContentCallback;
import io.jsguru.eusisdk.callbacks.EusiFormCallback;
import io.jsguru.eusisdk.callbacks.EusiLogInCallback;
import io.jsguru.eusisdk.callbacks.EusiRegisterCallback;
import io.jsguru.eusisdk.callbacks.EusiSubmitFormCallback;
import io.jsguru.eusisdk.callbacks.EusiTaxonomyCallback;
import io.jsguru.eusisdk.exceptions.EusiAuthException;
import io.jsguru.eusisdk.exceptions.EusiContentException;
import io.jsguru.eusisdk.exceptions.EusiSubmitFormException;
import io.jsguru.eusisdk.models.content.EusiContentResponse;
import io.jsguru.eusisdk.models.form.EusiFormResponse;
import io.jsguru.eusisdk.models.taxonomy.EusiTaxonomyResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Class provides main functionality of Eusi SDK
 * Created from Eusi object
 * Used for main interaction with Eusi Delivery API
 *
 * @author Petar Suvajac
 * @version 1.0
 * @see Eusi
 *
 * Created by Petar Suvajac on 3/8/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiClient {
    private String BASE_URL;
    private static final String API_KEYWORD= "api";
    private static final String SLASH_SEPARATOR = "/";
    private static final String ACTION_AUTHORIZE = "authorize";
    private static final String ACTION_REGISTER = "register";
    private static final String ACTION_LOGIN = "login";
    private static final String ACTION_CONTENT = "items";
    private static final String ACTION_FORMS = "forms";
    private static final String ACTION_TAXONOMY = "taxonomy";

    private Eusi eusi;

    // Bucket Id
    private String bucketID;

    // Bucket Secret
    private String bucketSecret;

    // Authentication token
    private String authToken;

    // User Token
    private String userToken;

    // Helpers
    private EusiNetworking eusiNetworking;
    private EusiParser eusiParser;

    /**
     * Constructor
     */
    private EusiClient(){}


    /**
     * Constructor
     * @param eusi Eusi
     * @param bucketID Bucket Id
     * @param bucketSecret Bucket Secret
     *
     * @see Eusi
     */
    EusiClient(Eusi eusi, String bucketID, String bucketSecret) {
        this.eusi = eusi;
        this.bucketID = bucketID;
        this.bucketSecret = bucketSecret;
        eusiNetworking = new EusiNetworking();
        eusiParser = new EusiParser();
        BASE_URL = getHost() + SLASH_SEPARATOR + API_KEYWORD + SLASH_SEPARATOR + getApiVersion()
                + SLASH_SEPARATOR + bucketID + SLASH_SEPARATOR;
    }


    /**
     * Authorise client (synchronously)
     * @return Authentication token
     * @throws IOException if there is networking problem
     * @throws EusiAuthException in case of bad parameters (eusi, buckedId, bucketSecret, ...)
     */
    public String authorize() throws IOException, EusiAuthException {
        try {
            String responseString = doAuthorize(null);

            if (EusiNetworking.haveError(responseString)) {
                throw new EusiAuthException(EusiNetworking.getErrorCode(responseString), EusiNetworking.getErrorMessage(responseString));
            }

            // Set tokens
            JSONObject response = new JSONObject(responseString);
            authToken = response.getString("token");
            userToken = null;

            return responseString;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Authorise client (asynchronously)
     * @param authorizeCallback Callback, will be called with result of authorisation
     *
     * @see EusiAuthorizeCallback
     */
    public void authorizeAsync(EusiAuthorizeCallback authorizeCallback) {
        try {
            doAuthorize(authorizeCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for authorize
    private String doAuthorize(final EusiAuthorizeCallback authorizeCallback) throws IOException {
        String url = BASE_URL + ACTION_AUTHORIZE;
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);

        Callback okHttpCallback = null;
        if (authorizeCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    authorizeCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        authorizeCallback.onFailure(EusiNetworking.getErrorMessage(responseString));
                    } else {
                        try {
                            JSONObject temp = new JSONObject(responseString);
                            String authToken = temp.getString("token");

                            // Set tokens
                            EusiClient.this.setAuthToken(authToken);
                            EusiClient.this.setUserToken(null);

                            // Call callback
                            authorizeCallback.onSuccess(responseString, authToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("secret", bucketSecret);

            return eusiNetworking.post(url, headers, requestBody.toString(), okHttpCallback);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Register new user (synchronously)
     * If registration is successful user is automatically logged in
     *
     * @param firstName First name of new user
     * @param lastName Last name of new user
     * @param email Email of new user
     * @param password Password of new user
     *
     * @return User token
     * @throws IOException if there is networking problem
     * @throws EusiAuthException if eusiClient isn't authorised
     */
    public String register(String firstName, String lastName, String email, String password) throws IOException, EusiAuthException {
        if (authToken == null)
            throw new EusiAuthException(EusiAuthException.ErrorAuth.ERROR_CODE_NOT_AUTHORISED, EusiAuthException.ErrorAuth.ERROR_NOT_AUTHORISED);

        String responseResult = doRegister(firstName, lastName, email, password, null);

        // Check if api returned error message
        if (EusiNetworking.haveError(responseResult))
            throw new EusiAuthException(EusiNetworking.getErrorCode(responseResult), EusiNetworking.getErrorMessage(responseResult));

        String token = null;
        try {
            // Set userToken
            JSONObject response = new JSONObject(responseResult);
            token = response.getString("token");
            setUserToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * Register new user (asynchronously)
     * If registration is successful user is automatically logged in
     *
     * @param firstName First name of new user
     * @param lastName Last name of new user
     * @param email Email of new user
     * @param password Password of new user
     * @param registerCallback Callback, will be called with result of registration
     *
     * @see EusiRegisterCallback
     */
    public void registerAsync(String firstName, String lastName, String email, String password, EusiRegisterCallback registerCallback) {
        if (authToken == null) {
            registerCallback.onFailure(EusiAuthException.ErrorAuth.ERROR_NOT_AUTHORISED);
            return;
        }

        try {
            doRegister(firstName, lastName, email, password, registerCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for register
    private String doRegister(String firstName, String lastName, String email, String password, final EusiRegisterCallback registerCallback) throws IOException {
        // Check for this before call
        if (authToken == null)
            return null;

        String url = BASE_URL + ACTION_REGISTER;
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);
        headers.put(EusiNetworking.AUTHORIZATION, "Bearer " + authToken);
        Callback okHttpCallback = null;
        if (registerCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    registerCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        registerCallback.onFailure(EusiNetworking.getErrorMessage(responseString));
                    } else {
                        try {
                            JSONObject temp = new JSONObject(responseString);
                            String userToken = temp.getString("token");

                            // Set tokens
                            EusiClient.this.setUserToken(userToken);

                            // Call callback
                            registerCallback.onSuccess(responseString, userToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("first_name", firstName);
            requestBody.put("last_name", lastName);

            return eusiNetworking.post(url, headers, requestBody.toString(), okHttpCallback);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Log in user (synchronously)
     * @param email Email of new user
     * @param password Password of new user
     *
     * @return User Token
     * @throws IOException if there is networking problem
     * @throws EusiAuthException if eusiClient isn't authorised, in case of bad parameters, ...
     */
    public String logIn(String email, String password) throws IOException, EusiAuthException {
        if (authToken == null)
            throw new EusiAuthException(EusiAuthException.ErrorAuth.ERROR_CODE_NOT_AUTHORISED, EusiAuthException.ErrorAuth.ERROR_NOT_AUTHORISED);

        String responseResult = doLogIn(email, password, null);

        // Check if api returned error message
        if (EusiNetworking.haveError(responseResult))
            throw new EusiAuthException(EusiNetworking.getErrorCode(responseResult), EusiNetworking.getErrorMessage(responseResult));

        String token = null;
        try {
            // Set userToken
            JSONObject response = new JSONObject(responseResult);
            token = response.getString("token");
            setUserToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * Log in user (asynchronously)
     * @param email Email of new user
     * @param password Password of new user
     * @param logInCallback Callback, will be called with result of log in
     *
     * @see EusiLogInCallback
     */
    public void logInAsync(String email, String password, EusiLogInCallback logInCallback){
        if (authToken == null) {
            logInCallback.onFailure(EusiAuthException.ErrorAuth.ERROR_NOT_AUTHORISED);
            return;
        }

        try {
            doLogIn(email, password, logInCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for logIn
    private String doLogIn(String email, String password, final EusiLogInCallback logInCallback) throws IOException {
        if (authToken == null)
            return null;

        String url = BASE_URL + ACTION_LOGIN;
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);
        headers.put(EusiNetworking.AUTHORIZATION, "Bearer " + authToken);
        Callback okHttpCallback = null;
        if (logInCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logInCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        logInCallback.onFailure(EusiNetworking.getErrorMessage(responseString));
                    } else {
                        try {
                            JSONObject temp = new JSONObject(responseString);
                            String userToken = temp.getString("token");

                            // Set tokens
                            EusiClient.this.setUserToken(userToken);

                            // Call callback
                            logInCallback.onSuccess(responseString, userToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);

            return eusiNetworking.post(url, headers, requestBody.toString(), okHttpCallback);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Log out user
     */
    public void logOut() {
        userToken = null;
    }

    /**
     * @return true if user is logged in, false otherwise
     */
    public boolean isUserLoggedIn() {
        return userToken != null;
    }


    /**
     * Get content from bucket (synchronously)
     * @param withMembersContent include members content is user is logged in
     *
     * @return EusiContentResponse object
     * @throws IOException if there is networking problem
     * @throws EusiContentException if eusiClient isn't authorised, in case of bad parameters, ...
     *
     * @see EusiContentResponse
     */
    public EusiContentResponse getContent(boolean withMembersContent) throws IOException, EusiContentException {
        return getContent(null, withMembersContent);
    }

    /**
     * Get content from bucket with option of filter query (synchronously)
     * @param query Filter Query
     * @param withMembersContent include members content is user is logged in
     *
     * @return EusiContentResponse object
     * @throws IOException if there is networking problem
     * @throws EusiContentException if eusiClient isn't authorised, in case of bad parameters, in case of invalid query ...
     *
     * @see EusiQuery
     * @see EusiContentResponse
     */
    public EusiContentResponse getContent(EusiQuery query, boolean withMembersContent) throws IOException, EusiContentException {
        if(authToken == null && !withMembersContent){
            throw new EusiContentException(EusiContentException.ErrorContent.ERROR_CODE_NOT_AUTHORIZED, EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);
        }

        String resultString = doGetContent(query, withMembersContent, null);

        if(EusiNetworking.haveError(resultString))
            throw new EusiContentException(EusiNetworking.getErrorCode(resultString), EusiNetworking.getErrorMessage(resultString));

        return eusiParser.parseContent(resultString);
    }

    /**
     * Get content from bucket (asynchronously)
     * @param withMembersContent include members content is user is logged in
     * @param contentCallback Callback, will be called with result and content
     *
     * @see EusiContentCallback
     */
    public void getContentAsync(boolean withMembersContent, EusiContentCallback contentCallback) {
        getContentAsync(null, withMembersContent, contentCallback);
    }

    /**
     * Get content from bucket with option of filter query (asynchronously)
     * @param query Filter Query
     * @param withMembersContent include members content is user is logged in
     * @param contentCallback Callback, will be called with result and content
     *
     * @see EusiQuery
     * @see EusiContentCallback
     */
    public void getContentAsync(EusiQuery query, boolean withMembersContent, EusiContentCallback contentCallback){
        if(authToken == null && !withMembersContent){
            contentCallback.onFailure(EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);
            return;
        }
        try {
            doGetContent(query, withMembersContent, contentCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for getContent
    private String doGetContent(EusiQuery query,  boolean withMembersContent, final EusiContentCallback contentCallback) throws IOException {
        String token = authToken;
        if (withMembersContent && isUserLoggedIn())
            token = userToken;

        String url = BASE_URL + ACTION_CONTENT;
        if(query != null)
            url += query.getQueryString();
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);
        headers.put(EusiNetworking.AUTHORIZATION, "Bearer " + token);
        Callback okHttpCallback = null;
        if (contentCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    contentCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        contentCallback.onFailure(EusiNetworking.getErrorMessage(responseString));
                    } else {
                        // Call callback
                        contentCallback.onSuccess(eusiParser.parseContent(responseString));
                    }
                }
            };
        }

        return eusiNetworking.get(url, headers, okHttpCallback);
    }


    /**
     * Get form by Form Id (synchronously)
     * @param formID Form Id
     * @return EusiFormResponse object
     * @throws IOException if there is networking problem
     * @throws EusiContentException if eusiClient isn't authorised, in case of bad parameters, in case of invalid form id, ...
     *
     * @see EusiFormResponse
     */
    public EusiFormResponse getForm(String formID) throws IOException, EusiContentException {
        if(authToken == null && userToken == null){
            throw new EusiContentException(EusiContentException.ErrorContent.ERROR_CODE_NOT_AUTHORIZED, EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);
        }

        String result = doGetForm(formID, null);

        // Check if api returned error message
        if (EusiNetworking.haveError(result))
            throw new EusiContentException(EusiNetworking.getErrorCode(result), EusiNetworking.getErrorMessage(result));

        return eusiParser.parseForm(result);
    }

    /**
     * Get form by Form Id (asynchronously)
     * @param formID Form Id
     * @param formCallback Callback, will be called with form result
     *
     * @see EusiFormCallback
     */
    public void getFormAsync(String formID, EusiFormCallback formCallback){
        if(authToken == null && userToken == null){
            formCallback.onFailure(EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);
            return;
        }
        try {
            doGetForm(formID, formCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for getForm
    private String doGetForm(final String formID, final EusiFormCallback formCallback) throws IOException {
        String token = authToken;
        if(token == null)
            token = userToken;

        String url = BASE_URL + ACTION_FORMS + SLASH_SEPARATOR + formID;
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);
        headers.put(EusiNetworking.AUTHORIZATION, "Bearer " + token);
        Callback okHttpCallback = null;
        if (formCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    formCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        formCallback.onFailure(EusiNetworking.getErrorMessage(responseString));
                    } else {
                        // Call callback
                        formCallback.onSuccess(eusiParser.parseForm(responseString));
                    }
                }
            };
        }

        return eusiNetworking.get(url, headers, okHttpCallback);
    }


    /**
     * Submit form (synchronously)
     * @param formID Form Id
     * @param formBody Form Body as JSON object
     * @return TODO !!!
     * @throws IOException if there is networking problem
     * @throws EusiSubmitFormException if eusiClient isn't authorised, in case of bad parameters, in case of invalid form id, in case of validation form failure, ...
     */
    public String submitForm(String formID, JSONObject formBody) throws IOException, EusiSubmitFormException {
        if(authToken == null && userToken == null)
            throw new EusiSubmitFormException(EusiContentException.ErrorContent.ERROR_CODE_NOT_AUTHORIZED, EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);

        String response = doSubmitForm(formID, formBody, null);

        // Check if api returned error message
        if (EusiNetworking.haveError(response))
            throw new EusiSubmitFormException(EusiNetworking.getErrorCode(response),
                    EusiNetworking.getErrorMessage(response),
                    EusiNetworking.getValidationMessage(response));

        return response;
    }

    /**
     * Submit form (asynchronously)
     * @param formID Form Id
     * @param formBody Form Body as JSON object
     * @param formCallback Callback, will be called with submit result
     *
     * @see EusiSubmitFormCallback
     */
    public void submitFormAsync(String formID, JSONObject formBody, EusiSubmitFormCallback formCallback){
        if(authToken == null && userToken == null)
            formCallback.onFailure(EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED, null);

        try {
            doSubmitForm(formID, formBody, formCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for submitForm
    private String doSubmitForm(final String formID, JSONObject formBody, final EusiSubmitFormCallback formCallback) throws IOException {
        String token = authToken==null?userToken:authToken;
        String url = BASE_URL + ACTION_FORMS + SLASH_SEPARATOR + formID;
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);
        headers.put(EusiNetworking.AUTHORIZATION, "Bearer " + token);
        Callback okHttpCallback = null;
        if (formCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    formCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage(), null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        formCallback.onFailure(EusiNetworking.getErrorMessage(responseString), EusiNetworking.getValidationMessage(responseString));
                    } else {
                        // Call callback
                        formCallback.onSuccess(responseString, formID);
                    }
                }
            };
        }

        return eusiNetworking.post(url, headers, formBody.toString(), okHttpCallback);
    }


    /**
     * Get taxonomy by Taxonomy Id (synchronously)
     * @param taxonomyID Taxonomy Id
     * @return EusiTaxonomyResponse object
     * @throws IOException if there is networking problem
     * @throws EusiContentException if eusiClient isn't authorised, in case of bad parameters, ...
     *
     * @see EusiTaxonomyResponse
     */
    public EusiTaxonomyResponse getTaxonomy(String taxonomyID) throws IOException, EusiContentException {
        if (authToken == null && userToken == null)
            throw new EusiContentException(EusiContentException.ErrorContent.ERROR_CODE_NOT_AUTHORIZED, EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);

        String result = doGetTaxonomy(taxonomyID, null);

        // Check if api returned error message
        if (EusiNetworking.haveError(result))
            throw new EusiContentException(EusiNetworking.getErrorCode(result), EusiNetworking.getErrorMessage(result));

        return eusiParser.parseTaxonomy(result);
    }

    /**
     * Get taxonomy by Taxonomy Id (asynchronously)
     * @param taxonomyID Taxonomy Id
     * @param taxonomyCallback Callback, will be called with taxonomy result
     *
     * @see EusiTaxonomyCallback
     */
    public void getTaxonomyAsync(String taxonomyID, EusiTaxonomyCallback taxonomyCallback){
        if(authToken == null && userToken == null){
            taxonomyCallback.onFailure(EusiContentException.ErrorContent.ERROR_NOT_AUTHORIZED);
            return;
        }
        try {
            doGetTaxonomy(taxonomyID, taxonomyCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper for getTaxonomy
    private String doGetTaxonomy(final String taxonomyID, final EusiTaxonomyCallback taxonomyCallback) throws IOException {
        String url = BASE_URL + ACTION_TAXONOMY + SLASH_SEPARATOR + taxonomyID;
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(EusiNetworking.CONTENT_TYPE, EusiNetworking.CONTENT_TYPE_JSON);
        headers.put(EusiNetworking.AUTHORIZATION, "Bearer " + authToken);
        Callback okHttpCallback = null;
        if (taxonomyCallback != null) {
            okHttpCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    taxonomyCallback.onFailure(e.getClass().getSimpleName() + " : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();

                    // Check if api returned error message
                    if (EusiNetworking.haveError(responseString)) {
                        taxonomyCallback.onFailure(EusiNetworking.getErrorMessage(responseString));
                    } else {
                        // Call callback
                        taxonomyCallback.onSuccess(eusiParser.parseTaxonomy(responseString));
                    }
                }
            };
        }

        return eusiNetworking.get(url, headers, okHttpCallback);
    }



    // Other Getters and Setters
    public String getHost() {
        return eusi.getHost();
    }

    public String getApiVersion() {
        return eusi.getApiVersion();
    }

    public String getToken() {
        if (userToken != null)
            return userToken;
        return authToken;
    }

    public String getAuthToken(){
        return authToken;
    }

    public String getUserToken(){
        return userToken;
    }

    public String getBucketID() {
        return bucketID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
