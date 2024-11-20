package lecturermain;
//TP072764

import java.io.Serializable;

public class PresentationSlot implements Serializable {
    private String date;
    private String slot;

    public PresentationSlot(String date, String slot) {
        this.date = date;
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public String getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Slot: " + slot;
    }
}
