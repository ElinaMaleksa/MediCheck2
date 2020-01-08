package com.app.medicheck.ui.notifications;

public class ContentNotifications {
    int idNot;
    String nameNot;
    String bestBeforeNot;
    String warning;

    public ContentNotifications(int idNot, String nameNot,String bestBeforeNot, String warning) {
        this.idNot = idNot;
        this.nameNot = nameNot;
        this.bestBeforeNot = bestBeforeNot;
        this.warning = warning;
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

    public String getWarning() {
        return warning;
    }
}
