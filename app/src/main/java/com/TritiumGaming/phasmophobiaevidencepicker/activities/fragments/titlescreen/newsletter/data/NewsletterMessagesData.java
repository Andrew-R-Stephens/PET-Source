package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NewsletterMessagesData {

    private boolean isReady = false;

    private String lastReadDate = "LOL";
    private String mostRecentDateFound = "LOSER";

    boolean requiresNotify;

    private NewsletterViewModel.InboxType type;
    private final ArrayList<NewsletterMessageData> messages = new ArrayList<>();

    public void setInboxType(NewsletterViewModel.InboxType type) {
        this.type = type;
    }

    public NewsletterViewModel.InboxType getInboxType() {
        return type;
    }

    public void add(NewsletterMessageData msg) {
        setMostRecentDate(msg.getDate());

        messages.add(msg);
    }

    public ArrayList<NewsletterMessageData> getMessages() {
        return messages;
    }

    public void setLastReadDate(String date) {

        if (date == null) {
            Log.d("Message Center", "Date is null");
            return;
        }

        SimpleDateFormat parser =
                new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat simpleFormatter =
                new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

        String output;
        try {
            output = simpleFormatter.format(parser.parse(date));
        } catch (ParseException e) {
            output = date;
        }

        lastReadDate = output;
    }

    public void setMostRecentDate(String date) {

        if (date == null) {
            return;
        }

        if(messages.size() == 0) {

            SimpleDateFormat parser =
                    new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
            SimpleDateFormat simpleFormatter =
                    new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

            String output = "";
            try {
                output = simpleFormatter.format(parser.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            mostRecentDateFound = output;

        }
    }

    public boolean compareDates() {
        return requiresNotify = !mostRecentDateFound.equalsIgnoreCase(lastReadDate);
    }

    public int getMessageCount() {
        return messages.size();
    }

    public String getLastReadDate() {
        return lastReadDate;
    }

    public String getMostRecentDate() {
        return mostRecentDateFound;
    }

    public void updateLastReadDate() {
        lastReadDate = mostRecentDateFound;

        compareDates();
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public boolean isReady() {
        return isReady;
    }

    public String toString() {
        String t = "";

        for (NewsletterMessageData m : messages) {
            t += "\n[" + m.getTitle() + " " + m.getDate() + " " + m.getDescription() + "]";
        }
        return t;
    }

}
