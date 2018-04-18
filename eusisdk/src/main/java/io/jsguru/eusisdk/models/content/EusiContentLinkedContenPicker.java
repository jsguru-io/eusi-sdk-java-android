package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.content.helpers.LinkedContent;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */
public class EusiContentLinkedContenPicker extends EusiContentTypePicker {
    private String name;
    private ArrayList<LinkedContent> linkedContent;
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LinkedContent> getLinkedContent() {
        return linkedContent;
    }

    public void setLinkedContent(ArrayList<LinkedContent> linkedContent) {
        this.linkedContent = linkedContent;
    }

    @Override
    public Class getType() {
        return EusiContentLinkedContenPicker.class;
    }
}
