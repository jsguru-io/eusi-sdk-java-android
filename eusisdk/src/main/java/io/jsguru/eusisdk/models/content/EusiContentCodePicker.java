package io.jsguru.eusisdk.models.content;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentCodePicker extends EusiContentType {
    private String name;
    private String text;
    private String language;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public Class getType() {
        return EusiContentCodePicker.class;
    }
}
