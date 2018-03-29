package io.jsguru.eusisdk.models.content;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentLocationPicker extends EusiContentType {
    private String name;
    private double latitude;
    private double longitude;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Class getType() {
        return EusiContentLocationPicker.class;
    }
}
