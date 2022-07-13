package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessageData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessagesData;

import java.util.ArrayList;

/**
 * TODO
 */
public class NewsletterViewModel extends ViewModel {

    private boolean isUpToDate = false;

    private boolean requiresNotify = false;

    private InboxType type = InboxType.GENERAL;
    private int currentMessageID = 0;
    private ArrayList<NewsletterMessagesData> inboxMessageList;

    /**
     * init method
     *
     * @param context
     */
    public void init(Context context) {

        SharedPreferences sharedPref = getSharedPreferences(context);

        setLastReadDate(InboxType.GENERAL,
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_newsletter_lastreaddate_general),
                        getInbox(InboxType.GENERAL).getLastReadDate()));

        setLastReadDate(InboxType.PET,
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_newsletter_lastreaddate_pet),
                        getInbox(InboxType.PET).getLastReadDate()));

        setLastReadDate(InboxType.PHASMOPHOBIA,
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_newsletter_lastreaddate_phas),
                        getInbox(InboxType.PHASMOPHOBIA).getLastReadDate()));

        saveToFile(context);

    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_newsletterFile_name),
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_newsletterFile_name),
                Context.MODE_PRIVATE);
        return sharedPref.edit();
    }

    public void setIsUpToDate(boolean isUpToDate) {
        this.isUpToDate = isUpToDate;
    }

    public boolean isUpToDate() {
        boolean isUpToDate = true;

        if(inboxMessageList == null) {
            return false;
        }

        for(NewsletterMessagesData inbox : inboxMessageList) {
            if (!inbox.isReady()) {
                isUpToDate = false;
                break;
            }
        }

        return isUpToDate;
    }

    public void addInbox(NewsletterMessagesData inbox, InboxType type) {
        if (inboxMessageList == null) {
            inboxMessageList = new ArrayList<>(10);
        }
        inbox.setInboxType(type);
        try {
            inboxMessageList.add(inbox);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public void chooseCurrentInbox(InboxType type) {
        this.type = type;
    }

    public NewsletterMessagesData getCurrentInbox() {
        if (inboxMessageList == null) {
            return null;
        }

        for (int i = 0; i < inboxMessageList.size(); i++) {
            if (inboxMessageList.get(i).getInboxType() == type) {
                return inboxMessageList.get(i);
            }
        }
        return null;
    }

    public InboxType getInboxType(int pos) {
        return InboxType.values()[pos];
    }

    public InboxType getCurrentInboxType() {
        return type;
    }

    public void setCurrentMessageId(int position) {
        currentMessageID = position;
    }

    public NewsletterMessageData getCurrentMessage() {
        return getCurrentInbox().getMessages().get(currentMessageID);
    }

    public NewsletterMessagesData getInbox(InboxType inboxType) {
        if(inboxMessageList == null) {
            return null;
        }

        NewsletterMessagesData[] list = new NewsletterMessagesData[inboxMessageList.size()];
        inboxMessageList.toArray(list);

        for(int i = 0; i < list.length; i++) {
            if(list[i].getInboxType() == inboxType) {
                return inboxMessageList.get(i);
            }
        }

        return null;
    }

    public void compareDates(InboxType inboxType) {
        if(getInbox(inboxType).compareDates()) {
            requiresNotify = true;
        }
    }

    public void compareDates() {
        requiresNotify =
                (getInbox(InboxType.GENERAL) != null &&
                        getInbox(InboxType.GENERAL).compareDates()) ||
                (getInbox(InboxType.PET) != null &&
                        getInbox(InboxType.PET).compareDates()) ||
                (getInbox(InboxType.PHASMOPHOBIA) != null &&
                        getInbox(InboxType.PHASMOPHOBIA).compareDates());
    }

    public void updateLastReadDate() {
        getInbox(InboxType.GENERAL).updateLastReadDate();
        getInbox(InboxType.PET).updateLastReadDate();
        getInbox(InboxType.PHASMOPHOBIA).updateLastReadDate();
    }

    public boolean requiresNotify() {
        return requiresNotify;
    }

    public enum InboxType {
        GENERAL(0), PET(1), PHASMOPHOBIA(2);

        int id;

        InboxType(int id) {
            this.id = id;
        }

        public String getName(Context context) {
            String[] name =
                    context.getResources().getStringArray(R.array.messagecenter_inboxtitles);

            return name[id];
        }
    }

    public void setLastReadDate(InboxType inboxType, String date) {
        getInbox(inboxType).setLastReadDate(date);
    }

    public String getLastReadDate(InboxType inboxType) {
        return getInbox(inboxType).getLastReadDate();
    }

    public void setMostRecentDate(InboxType inboxType, String date) {
        NewsletterMessagesData inbox = getInbox(inboxType);
        if(inbox != null) {
            inbox.setMostRecentDate(date);
        }
    }

    public String getMostRecentDate(InboxType inboxType) {
        if(getInbox(inboxType) == null) {
            return "NA";
        }
        return getInbox(inboxType).getMostRecentDate();
    }

    public boolean areAllInboxesReady() {
        NewsletterMessagesData inbox1 = getInbox(InboxType.GENERAL);
        NewsletterMessagesData inbox2 = getInbox(InboxType.PET);
        NewsletterMessagesData inbox3 = getInbox(InboxType.PHASMOPHOBIA);

        if(inbox1 == null || inbox2 == null || inbox3 == null) {
            return false;
        }

        return
                inbox1.isReady() &&
                inbox2.isReady() &&
                inbox3.isReady();
    }

    private void saveLastReadDate(
            Context c, SharedPreferences.Editor editor, boolean localApply, InboxType inboxType) {
        if (editor == null) {
            editor = getEditor(c);
        }

        String target =
                c.getResources().getString(R.string.preference_newsletter_lastreaddate_general);
        switch (inboxType) {
            case PET: {
                target = c.getResources().getString(R.string.preference_newsletter_lastreaddate_pet);
                break;
            }
            case PHASMOPHOBIA: {
                target = c.getResources().getString(R.string.preference_newsletter_lastreaddate_phas);
                break;
            }
        }

        editor.putString(
                target,
                getLastReadDate(inboxType));

        Log.d("MessageCenter", "Saving...");

        if (localApply) {
            editor.apply();
        }
    }

    /**
     * saveToFile method
     *
     * @param context
     */
    public void saveToFile(Context context) {

        SharedPreferences.Editor editor = getEditor(context);

        saveLastReadDate(context, editor, false, InboxType.GENERAL);
        saveLastReadDate(context, editor, false, InboxType.PHASMOPHOBIA);
        saveLastReadDate(context, editor, false, InboxType.PET);

        editor.apply();
    }

    /**
     * saveToFile method
     *
     * @param context
     */
    public void saveToFile(Context context, InboxType inboxType) {

        SharedPreferences.Editor editor = getEditor(context);

        saveLastReadDate(context, editor, false, inboxType);

        editor.apply();
    }

}
