package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

public class InboxMessage {

    private String title, description, date;

    public InboxMessage(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public InboxMessage() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

}
