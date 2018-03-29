package io.jsguru.eusisdk.models.content.helpers;

/**
 * Created by Petar Suvajac on 3/21/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class Media extends ContentHelper {
    private String type;
    private int width;
    private int height;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
