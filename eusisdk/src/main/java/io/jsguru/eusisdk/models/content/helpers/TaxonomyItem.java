package io.jsguru.eusisdk.models.content.helpers;

import java.util.ArrayList;

/**
 * Created by Petar Suvajac on 3/21/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class TaxonomyItem {
    private String id;
    private String name;
    private String path;
    //TODO
    private ArrayList<TaxonomyItem> children;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
