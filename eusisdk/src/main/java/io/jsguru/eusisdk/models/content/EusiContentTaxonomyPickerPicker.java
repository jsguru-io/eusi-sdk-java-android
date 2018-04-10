package io.jsguru.eusisdk.models.content;

import java.util.ArrayList;

import io.jsguru.eusisdk.models.content.helpers.TaxonomyItem;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiContentTaxonomyPickerPicker extends EusiContentTypePicker {
    private String taxonomyId;
    private String taxonomyKey;
    private String pickerName;
    private String taxonomyName;
    private ArrayList<TaxonomyItem> taxonomyItems = new ArrayList<>();
    private ArrayList<String> taxonomyItemsList = new ArrayList<>();

    public String getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(String taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public String getTaxonomyKey() {
        return taxonomyKey;
    }

    public void setTaxonomyKey(String taxonomyKey) {
        this.taxonomyKey = taxonomyKey;
    }

    public String getPickerName() {
        return pickerName;
    }

    public void setPickerName(String pickerName) {
        this.pickerName = pickerName;
    }

    public String getTaxonomyName() {
        return taxonomyName;
    }

    public void setTaxonomyName(String taxonomyName) {
        this.taxonomyName = taxonomyName;
    }

    public ArrayList<TaxonomyItem> getTaxonomyItems() {
        return taxonomyItems;
    }

    public void setTaxonomyItems(ArrayList<TaxonomyItem> taxonomyItems) {
        this.taxonomyItems = taxonomyItems;
    }

    public ArrayList<String> getTaxonomyItemsList() {
        return taxonomyItemsList;
    }

    public void setTaxonomyItemsList(ArrayList<String> taxonomyItemsList) {
        this.taxonomyItemsList = taxonomyItemsList;
    }

    @Override
    public Class getType() {
        return EusiContentTaxonomyPickerPicker.class;
    }
}
