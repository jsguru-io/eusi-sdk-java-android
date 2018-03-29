package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.content.helpers.Document;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentDocumentPicker extends EusiContentType {
    private String name;
    private ArrayList<Document> documents = new ArrayList<>();
    private ArrayList<String> documentList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public ArrayList<String> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<String> documentList) {
        this.documentList = documentList;
    }


    @Override
    public Class getType() {
        return EusiContentDocumentPicker.class;
    }
}
