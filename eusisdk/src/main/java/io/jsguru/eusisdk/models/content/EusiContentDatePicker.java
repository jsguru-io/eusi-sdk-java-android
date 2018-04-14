package io.jsguru.eusisdk.models.content;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiContentDatePicker extends EusiContentTypePicker {
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
