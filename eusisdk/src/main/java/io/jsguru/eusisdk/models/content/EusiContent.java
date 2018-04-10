package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiContent {
    private String id;
    private String name;
    private String key;
    private String templateID;
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

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }
}
