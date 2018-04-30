package io.jsguru.eusisdk.models.form;

import java.util.ArrayList;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiFormResponse {
    private String id;
    private String name;
    private String key;
    private String redirectUrl;
    private String bucketId;
    private String responseString;
    private ArrayList<EusiFormField> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public ArrayList<EusiFormField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<EusiFormField> fields) {
        this.fields = fields;
    }
}
