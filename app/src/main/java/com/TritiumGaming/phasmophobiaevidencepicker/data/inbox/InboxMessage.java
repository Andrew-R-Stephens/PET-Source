package com.TritiumGaming.phasmophobiaevidencepicker.data.inbox;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontStyler;

public class InboxMessage {

    private String title, description, date;

    public InboxMessage(String title, String description, String date) {
        setTitle(FontStyler.removeXMLImgSrcTags(title));
        setDescription(FontStyler.removeXMLImgSrcTags(description));
        setDate(FontStyler.removeXMLPubDateClockTime(FontStyler.removeXMLPubDateClockTime(date)));
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

    public boolean hasContent() {
        return hasTitle() || hasDescription() || hasDate();
    }

    private boolean hasDate() {
        return getDate() != null && !getDate().equals("");
    }

    private boolean hasDescription() {
        return getDescription() != null && !getDescription().equals("");
    }

    private boolean hasTitle() {
        return getTitle() != null && !getTitle().equals("");
    }


}
