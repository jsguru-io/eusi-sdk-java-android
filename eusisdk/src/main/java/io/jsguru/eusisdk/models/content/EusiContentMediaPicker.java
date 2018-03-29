package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.content.helpers.Document;
import io.jsguru.eusisdk.models.content.helpers.Media;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentMediaPicker extends EusiContentType {
    private String name;
    private ArrayList<Media> media = new ArrayList<>();
    private ArrayList<String> mediaList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    public ArrayList<String> getMediaList() {
        return mediaList;
    }

    public void setMediaList(ArrayList<String> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public Class getType() {
        return EusiContentMediaPicker.class;
    }
}
