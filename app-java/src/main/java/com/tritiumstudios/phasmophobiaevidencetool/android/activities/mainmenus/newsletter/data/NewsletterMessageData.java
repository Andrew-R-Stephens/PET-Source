package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.newsletter.data;

import com.tritiumstudios.phasmophobiaevidencetool.android.data.utilities.FontUtils;

public class NewsletterMessageData {

    private String title, description, date;

    public NewsletterMessageData(String title, String description, String date) {
        setTitle(FontUtils.removeXMLImgSrcTags(title));
        setDescription(FontUtils.removeXMLImgSrcTags(description));
        setDate(FontUtils.removeXMLPubDateClockTime(FontUtils.removeXMLPubDateClockTime(date)));
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
