package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiContent {
    private String id;
    private String title;
    private String key;
    private String contentModelId;
    private long publishedAt;
    private ArrayList<EusiContentTypePicker> contentList = new ArrayList<>();

    public ArrayList<EusiContentTypePicker> getContent() {
        return contentList;
    }

    public void setContent(ArrayList<EusiContentTypePicker> contentList) {
        this.contentList = contentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContentModelId() {
        return contentModelId;
    }

    public void setContentModelId(String contentModelId) {
        this.contentModelId = contentModelId;
    }


    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }
}
