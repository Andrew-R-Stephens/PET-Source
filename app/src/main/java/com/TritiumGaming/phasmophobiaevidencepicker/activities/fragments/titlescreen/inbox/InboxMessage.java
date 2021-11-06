package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

import android.util.Log;

public class InboxMessage {

    private String title, description, date;

    public InboxMessage(String title, String description, String date) {
        setTitle(cleanupHTML(title));
        setDescription(cleanupHTML(description));
        setDate(cleanupHTML(date));
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

    /**
     * cleanupHTML
     *
     * Removes img sources. Pretty inefficient, but fine for now.
     *
     * @param msg - raw HTML message
     * @return trimmedHTML
     */
    private String cleanupHTML(String msg) {
        int indexStart = msg.indexOf("<img src=");
        while(indexStart >= 0) {
            Log.d("MsgOutput", indexStart + "");
            String newStr = msg.substring(indexStart);
            int indexEnd = newStr.indexOf("/>");
            newStr = msg.substring(indexStart, indexStart + indexEnd + 2);
            Log.d("MsgOutput", indexEnd + "");
            Log.d("MsgOutputSubstring", newStr);
            msg = msg.replaceFirst(newStr, "");

            indexStart = msg.indexOf("<img src=");
        }
        return msg;
    }

}
