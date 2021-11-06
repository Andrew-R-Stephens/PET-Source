package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

import android.provider.Telephony;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;

import java.util.ArrayList;

public class InboxMessageList {

    private MessageCenterViewModel.InboxType type;
    private ArrayList<InboxMessage> messages = new ArrayList<>();

    public void setInboxType(MessageCenterViewModel.InboxType type) {
        this.type = type;
    }

    public MessageCenterViewModel.InboxType getInboxType() {
        return type;
    }

    public void add(InboxMessage msg){
        messages.add(msg);
    }

    public ArrayList<InboxMessage> getMessages() {
        return messages;
    }

    public int getMessageCount() {
        return messages.size();
    }

    public String toString() {
        String t = "";

        for(InboxMessage m: messages)
            t += "\n[" + m.getTitle() + " " + m.getDate() + " " + m.getDescription() + "]";

        return t;
    }
}
