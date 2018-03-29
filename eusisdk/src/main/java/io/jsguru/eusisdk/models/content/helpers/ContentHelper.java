package io.jsguru.eusisdk.models.content.helpers;

/**
 * Created by Petar Suvajac on 3/21/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public abstract class ContentHelper {
    protected String id;
    protected String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
