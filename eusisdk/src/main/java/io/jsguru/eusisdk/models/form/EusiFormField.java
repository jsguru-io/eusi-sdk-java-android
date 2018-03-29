package io.jsguru.eusisdk.models.form;

import java.util.ArrayList;

/**
 * Created by Petar Suvajac on 3/26/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiFormField {
    private String key;
    private String type;
    private boolean required;
    private ArrayList<String> allowedValues;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ArrayList<String> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(ArrayList<String> allowedValues) {
        this.allowedValues = allowedValues;
    }
}
