package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsletterMessagesData {

    private boolean isReady = false;

    private String lastReadDate = "NA";
    @NonNull
    private String mostRecentDateFound = "NA";

    boolean requiresNotify;

    private NewsletterViewModel.InboxType type;
    private final ArrayList<NewsletterMessageData> messages = new ArrayList<>();

    public void setInboxType(NewsletterViewModel.InboxType type) {
        this.type = type;
    }

    public NewsletterViewModel.InboxType getInboxType() {
        return type;
    }

    public void add(@NonNull NewsletterMessageData msg) {
        setMostRecentDate(msg.getDate());

        messages.add(msg);
    }

    @NonNull
    public ArrayList<NewsletterMessageData> getMessages() {
        return messages;
    }

    public void setLastReadDate(@Nullable String date) {

        if (date == null) {
            Log.d("Message Center", "Date is null");
            return;
        }

        SimpleDateFormat parser =
                new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat simpleFormatter =
                new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

        String output = "NA";
        try {
            Date formattedDate = parser.parse(date);
            if(formattedDate != null) {
                output = simpleFormatter.format(formattedDate);
            }
        } catch (ParseException e) {
            output = date;
        }

        lastReadDate = output;
    }

    public void setMostRecentDate(@Nullable String date) {

        if (date == null) {
            return;
        }

        if(messages.isEmpty()) {

            SimpleDateFormat parser =
                    new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
            SimpleDateFormat simpleFormatter =
                    new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

            String output = "";
            try {
                Date parsedDate = parser.parse(date);
                if(parsedDate != null) {
                    output = simpleFormatter.format(parsedDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            mostRecentDateFound = output;

        }
    }

    public boolean compareDates() {
        return requiresNotify = !mostRecentDateFound.equalsIgnoreCase(lastReadDate);
    }

    public String getLastReadDate() {
        return lastReadDate;
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

    @NonNull
    public String toString() {
        StringBuilder t = new StringBuilder();

        for (NewsletterMessageData m : messages) {
            t.append("\n[").append(m.getTitle()).append(" ").append(m.getDate()).append(" ").append(m.getDescription()).append("]");
        }
        return t.toString();
    }

}
