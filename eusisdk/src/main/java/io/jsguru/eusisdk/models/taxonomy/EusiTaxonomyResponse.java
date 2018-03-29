package io.jsguru.eusisdk.models.taxonomy;

import java.util.ArrayList;

/**
 * Created by Petar Suvajac on 3/26/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiTaxonomyResponse {
    private String id;
    private String name;
    private String key;
    private String bucketId;
    private String responseString;
    private ArrayList<EusiTaxonomy> taxonomyItems;

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

    public ArrayList<EusiTaxonomy> getTaxonomyItems() {
        return taxonomyItems;
    }

    public void setTaxonomyItems(ArrayList<EusiTaxonomy> taxonomyItems) {
        this.taxonomyItems = taxonomyItems;
    }
}
