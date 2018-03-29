package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.EusiPagination;

/**
 * Created by Petar Suvajac on 3/22/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentResponse {
    private ArrayList<EusiContent> contentList = new ArrayList<>();
    private EusiPagination pagination;
    private String responseString;

    public ArrayList<EusiContent> getContentList() {
        return contentList;
    }

    public void setContentList(ArrayList<EusiContent> contentList) {
        this.contentList = contentList;
    }

    public EusiPagination getPagination() {
        return pagination;
    }

    public void setPagination(EusiPagination pagination) {
        this.pagination = pagination;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }
}
