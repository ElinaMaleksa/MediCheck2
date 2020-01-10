package com.app.medicheck.ui.notifications;

public class ContentNotifications {
    int idNot;
    String nameNot;
    String bestBeforeNot;
    long daysDiff;

    public ContentNotifications(int idNot, String nameNot ,String bestBeforeNot, long daysDiff) {
        this.idNot = idNot;
        this.nameNot = nameNot;
        this.bestBeforeNot = bestBeforeNot;
        this.daysDiff = daysDiff;
    }

    public int getIdNot() {
        return idNot;
    }

    public String getNameNot() {
        return nameNot;
    }

    public String getBestBeforeNot() {
        return bestBeforeNot;
    }

    public long getDaysDiff() {
        return daysDiff;
    }
}
