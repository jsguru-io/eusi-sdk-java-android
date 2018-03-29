package io.jsguru.eusisdk.models.content;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentTextPicker extends EusiContentType {
    private String name;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public Class getType() {
        return EusiContentTextPicker.class;
    }
}
