package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox.InboxMessage;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox.InboxMessageList;

import java.util.ArrayList;

/**
 * TODO
 */
public class MessageCenterViewModel extends ViewModel {

    private boolean isUpToDate = false;

    private InboxType type = InboxType.GENERAL;
    private int currentMessageID = 0;
    private ArrayList<InboxMessageList> inboxMessageList;

    public void setIsUpToDate(boolean isUpToDate){
        this.isUpToDate = isUpToDate;
    }

    public boolean isUpToDate() {
        return isUpToDate;
    }

    public void addInbox(InboxMessageList inbox, InboxType type) {
        if(inboxMessageList == null)
            inboxMessageList = new ArrayList<>();

        inbox.setInboxType(type);
        inboxMessageList.add(inbox);
    }

    public void chooseCurrentInbox(InboxType type){
        this.type = type;
    }

    public int getInboxCount() {
        if(inboxMessageList == null)
            return 0;
        return inboxMessageList.size();
    }

    public InboxMessageList getCurrentInbox(int index) {
        return inboxMessageList.get(index);
    }

    public InboxMessageList getCurrentInbox() {
        if(inboxMessageList == null)
            return null;

        for(int i = 0; i < inboxMessageList.size(); i++){
            if(inboxMessageList.get(i).getInboxType() == type){
                return inboxMessageList.get(i);
            }
        }
        return null;
    }

    public InboxType getCurrentInboxType() {
        return type;
    }

    public void setCurrentMessageId(int position) {
        currentMessageID = position;
    }

    public InboxMessage getCurrentMessage() {
        return getCurrentInbox().getMessages().get(currentMessageID);
    }

    public enum InboxType {
        GENERAL(0), PET(1), PHASMOPHOBIA(2);
        private final String[] name = {"General News", "P.E.T. Updates", "Official Phasmophobia Updates"};

        int id;

        InboxType(int id) {
            this.id = id;
        }

        public String getName(){
            return name[id];
        }
    }
}
