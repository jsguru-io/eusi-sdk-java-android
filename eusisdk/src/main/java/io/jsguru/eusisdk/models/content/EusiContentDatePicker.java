package io.jsguru.eusisdk.models.content;

/**
 * Created by Petar Suvajac on 3/20/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiContentDatePicker extends EusiContentType {
    private String name;
    private long timeInMillis;
    private String timeString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return timeInMillis;
    }

    public void setTime(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public Class getType() {
        return EusiContentDatePicker.class;
    }
}
