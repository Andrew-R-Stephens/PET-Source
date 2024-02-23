package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessageData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessagesData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParserUtils;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

/**
 * TODO
 */
public class NewsletterViewModel extends ViewModel {

    private static final @StringRes int fileName = R.string.preferences_newsletterFile_name;

    private boolean requiresNotify = false;

    private InboxType type = InboxType.GENERAL;
    private int currentMessageID = 0;
    private ArrayList<NewsletterMessagesData> inboxMessageList;

    /**
     * init method
     *
     * @param context - The current Activity's context
     */
    public boolean init(@NonNull Context context) {

        SharedPreferences sharedPref = getSharedPreferences(context);

        NewsletterMessagesData inbox;
        if((inbox = getInbox(InboxType.GENERAL)) != null) {
            setLastReadDate(InboxType.GENERAL,
                    sharedPref.getString(
                            context.getString(R.string.preference_newsletter_lastreaddate_general),
                            inbox.getLastReadDate()));
        }

        if((inbox = getInbox(InboxType.PET)) != null) {
            setLastReadDate(InboxType.PET,
                    sharedPref.getString(
                            context.getString(R.string.preference_newsletter_lastreaddate_pet),
                            inbox.getLastReadDate()));
        }

        if((inbox = getInbox(InboxType.PHASMOPHOBIA)) != null) {
            setLastReadDate(InboxType.PHASMOPHOBIA,
                    sharedPref.getString(
                            context.getString(R.string.preference_newsletter_lastreaddate_phas),
                            inbox.getLastReadDate()));
        }

        saveToFile(context);

        return true;

    }

    public void registerInboxes(@NonNull Context context) {
        try {
            new RSSParserUtils(XmlPullParserFactory.newInstance(),
                    context.getString(R.string.preference_phasmophobia_changelog_link),
                    NewsletterViewModel.InboxType.PHASMOPHOBIA, this);

            new RSSParserUtils(XmlPullParserFactory.newInstance(),
                    context.getString(R.string.preference_general_news_link),
                    NewsletterViewModel.InboxType.GENERAL, this);

            new RSSParserUtils(XmlPullParserFactory.newInstance(),
                    context.getString(R.string.preference_pet_changelog_link),
                    NewsletterViewModel.InboxType.PET, this);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(
                context.getString(fileName),
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(fileName),
                Context.MODE_PRIVATE);
        return sharedPref.edit();
    }

    public boolean isUpToDate() {
        if(inboxMessageList == null) {
            return false;
        }

        boolean isUpToDate = true;
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

        try {
            inbox.setInboxType(type);
            inboxMessageList.add(inbox);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public void setCurrentInboxType(InboxType type) {
        this.type = type;
    }

    public InboxType getCurrentInboxType() {
        return type;
    }

    public NewsletterMessagesData getCurrentInbox() {
        if (inboxMessageList == null) {
            return null;
        }

        for (int i = 0; i < inboxMessageList.size(); i++) {
            if (inboxMessageList.get(i) != null &&
                    inboxMessageList.get(i).getInboxType() == type) {
                return inboxMessageList.get(i);
            }
        }

        return null;
    }

    public InboxType getInboxType(int pos) {
        return InboxType.values()[pos];
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

    public void setCurrentMessageId(int position) {
        currentMessageID = position;
    }

    public NewsletterMessageData getCurrentMessage() {
        if(getCurrentInbox() == null || getCurrentInbox().getMessages() == null) {
            return null;
        }
        return getCurrentInbox().getMessages().get(currentMessageID);
    }

    public void setLastReadDate(InboxType inboxType, String date) {
        if(getInbox(inboxType) == null) {
            return;
        }

        getInbox(inboxType).setLastReadDate(date);
    }

    public String getLastReadDate(InboxType inboxType) {
        if(getInbox(inboxType) == null) {
            return "NA";
        }

        return getInbox(inboxType).getLastReadDate();
    }

    public void compareAllInboxDates() {
        boolean general = (getInbox(InboxType.GENERAL) != null &&
                getInbox(InboxType.GENERAL).compareDates());
        boolean pet = (getInbox(InboxType.PET) != null &&
                getInbox(InboxType.PET).compareDates());
        boolean phasmophobia = (getInbox(InboxType.PHASMOPHOBIA) != null &&
                getInbox(InboxType.PHASMOPHOBIA).compareDates());

        requiresNotify = general || pet || phasmophobia;
    }

    public boolean requiresNotify() {
        return requiresNotify;
    }

    /** @noinspection SameParameterValue*/
    private void saveLastReadDate(
            @NonNull Context c, SharedPreferences.Editor editor, boolean localApply,
            InboxType inboxType) {

        if (editor == null) {
            editor = getEditor(c);
        }

        String target = "";
        switch (inboxType) {
            case PET -> {
                target = c.getResources().getString(R.string.preference_newsletter_lastreaddate_pet);
            }
            case PHASMOPHOBIA -> {
                target = c.getResources().getString(R.string.preference_newsletter_lastreaddate_phas);
            }
            case GENERAL -> {
                target = c.getResources().getString(R.string.preference_newsletter_lastreaddate_general);
            }
        }

        editor.putString(
                target,
                getLastReadDate(inboxType));

        if (localApply) {
            editor.apply();
        }
    }

    /**
     * saveToFile method
     */
    public void saveToFile(@NonNull Context context) {

        SharedPreferences.Editor editor = getEditor(context);

        saveLastReadDate(context, editor, false, InboxType.GENERAL);
        saveLastReadDate(context, editor, false, InboxType.PHASMOPHOBIA);
        saveLastReadDate(context, editor, false, InboxType.PET);

        editor.apply();

        Log.d("MessageCenter", "Saving all inboxes...");
    }

    /**
     * saveToFile method
     */
    public void saveToFile(Context context, InboxType inboxType) {

        SharedPreferences.Editor editor = getEditor(context);

        saveLastReadDate(context, editor, false, inboxType);

        editor.apply();

        Log.d("MessageCenter", "Saving [" + inboxType.name() + "]...");
    }

    public enum InboxType {
        GENERAL(0), PET(1), PHASMOPHOBIA(2);

        final int id;

        InboxType(int id) {
            this.id = id;
        }

        public String getName(@NonNull Context context) {
            String[] name =
                    context.getResources().getStringArray(R.array.messagecenter_inboxtitles);

            return name[id];
        }

        public int getIcon(@NonNull Context context) {
            TypedArray typed_inbox_icons = context.getResources().obtainTypedArray(R.array.messagecenter_inboxicons);

            @DrawableRes int icon = typed_inbox_icons.getResourceId(id, 0);
            typed_inbox_icons.recycle();

            return icon;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(NewsletterMessagesData data: inboxMessageList) {
            stringBuilder.append(data).append("\n");
        }

        return stringBuilder.toString();
    }

}
