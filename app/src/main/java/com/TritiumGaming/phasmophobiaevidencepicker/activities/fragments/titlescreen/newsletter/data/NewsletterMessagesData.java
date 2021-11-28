package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;

import java.util.ArrayList;

public class NewsletterMessagesData {

    private NewsletterViewModel.InboxType type;
    private final ArrayList<NewsletterMessageData> messages = new ArrayList<>();

    public void setInboxType(NewsletterViewModel.InboxType type) {
        this.type = type;
    }

    public NewsletterViewModel.InboxType getInboxType() {
        return type;
    }

    public void add(NewsletterMessageData msg) {
        messages.add(msg);
    }

    public ArrayList<NewsletterMessageData> getMessages() {
        return messages;
    }

    public int getMessageCount() {
        return messages.size();
    }

    public String toString() {
        String t = "";

        for (NewsletterMessageData m : messages)
            t += "\n[" + m.getTitle() + " " + m.getDate() + " " + m.getDescription() + "]";

        return t;
    }
}
