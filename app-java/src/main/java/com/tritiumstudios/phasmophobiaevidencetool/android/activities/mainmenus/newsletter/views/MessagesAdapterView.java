package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.newsletter.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.newsletter.data.NewsletterMessageData;

import java.util.ArrayList;

public class MessagesAdapterView
        extends RecyclerView.Adapter<MessagesAdapterView.ViewHolder> {

    private final ArrayList<NewsletterMessageData> messages;
    private final OnMessageListener onMessageListener;

    public MessagesAdapterView(
            ArrayList<NewsletterMessageData> messages,
            OnMessageListener onMessageListener) {
        this.messages = messages;
        this.onMessageListener = onMessageListener;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final AppCompatTextView label_messageTitle;
        private final OnMessageListener onMessageListener;

        public ViewHolder(@NonNull View view, OnMessageListener onMessageListener) {
            super(view);
            label_messageTitle = itemView.findViewById(R.id.textView_messageName);
            view.setOnClickListener(this);
            this.onMessageListener = onMessageListener;
        }

        @Override
        public void onClick(View v) {
            this.onMessageListener.onNoteClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(
                R.layout.item_newsletter_inbox_message, parent, false);
        return new ViewHolder(messageView, this.onMessageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppCompatTextView textView = holder.label_messageTitle;
        textView.setText(messages.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public interface OnMessageListener {
        void onNoteClick(int position);
    }

}