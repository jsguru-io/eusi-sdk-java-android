package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.content.helpers.Document;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiContentDocumentPicker extends EusiContentTypePicker {
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
