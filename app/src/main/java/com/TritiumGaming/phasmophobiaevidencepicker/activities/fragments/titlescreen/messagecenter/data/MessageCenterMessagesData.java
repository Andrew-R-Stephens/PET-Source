package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.messagecenter.data;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;

import java.util.ArrayList;

public class MessageCenterMessagesData {

    private MessageCenterViewModel.InboxType type;
    private ArrayList<MessageCenterMessageData> messages = new ArrayList<>();

    public void setInboxType(MessageCenterViewModel.InboxType type) {
        this.type = type;
    }

    public MessageCenterViewModel.InboxType getInboxType() {
        return type;
    }

    public void add(MessageCenterMessageData msg){
        messages.add(msg);
    }

    public ArrayList<MessageCenterMessageData> getMessages() {
        return messages;
    }

    public int getMessageCount() {
        return messages.size();
    }

    public String toString() {
        String t = "";

        for(MessageCenterMessageData m: messages)
            t += "\n[" + m.getTitle() + " " + m.getDate() + " " + m.getDescription() + "]";

        return t;
    }
}
