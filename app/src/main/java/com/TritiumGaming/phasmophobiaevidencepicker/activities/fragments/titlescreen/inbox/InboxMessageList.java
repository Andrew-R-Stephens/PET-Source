package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

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

}
