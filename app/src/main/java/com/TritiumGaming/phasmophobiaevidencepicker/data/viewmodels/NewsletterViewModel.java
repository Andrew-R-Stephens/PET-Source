package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;

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

    private InboxType type = InboxType.GENERAL;
    private int currentMessageID = 0;
    private ArrayList<NewsletterMessagesData> inboxMessageList;

    public void setIsUpToDate(boolean isUpToDate) {
        this.isUpToDate = isUpToDate;
    }

    public boolean isUpToDate() {
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
}
