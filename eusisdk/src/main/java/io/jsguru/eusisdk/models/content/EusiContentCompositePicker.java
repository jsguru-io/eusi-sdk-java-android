package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */
public class EusiContentCompositePicker extends EusiContentTypePicker {
    private String name;
    private ArrayList<ArrayList<EusiContentTypePicker>> lists;
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ArrayList<EusiContentTypePicker>> getLists() {
        return lists;
    }

    public void setLists(ArrayList<ArrayList<EusiContentTypePicker>> lists) {
        this.lists = lists;
    }

    @Override
    public Class getType() {
        return EusiContentCompositePicker.class;
    }
}
