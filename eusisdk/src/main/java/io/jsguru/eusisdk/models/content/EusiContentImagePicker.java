package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.content.helpers.Image;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentImagePicker extends EusiContentType {
    private String name;
    private ArrayList<Image> images = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public Class getType() {
        return EusiContentImagePicker.class;
    }
}
