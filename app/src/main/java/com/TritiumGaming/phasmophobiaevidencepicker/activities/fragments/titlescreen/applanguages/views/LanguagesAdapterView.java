package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages.
        views;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

public class LanguagesAdapterView extends RecyclerView.Adapter<LanguagesAdapterView.ViewHolder> {
    private static int mPreviousIndex = 0;
    private final ArrayList<String> languages;
    private final OnLanguageListener onLanguageListener;

    public LanguagesAdapterView(
            ArrayList<String> languages,
            int selected,
            OnLanguageListener onLanguageListener) {
        mPreviousIndex = selected;
        this.languages = languages;
        this.onLanguageListener = onLanguageListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatTextView label_languageTitle;
        public AppCompatImageView image;
        private final OnLanguageListener onLanguageListener;

        public ViewHolder(View view, OnLanguageListener onLanguageListener) {
            super(view);
            label_languageTitle = itemView.findViewById(R.id.textView_languageName);
            image = itemView.findViewById(R.id.imageView_languageChoiceIcon);
            view.setOnClickListener(this);
            this.onLanguageListener = onLanguageListener;
        }

        @Override
        public void onClick(View v) {
            this.onLanguageListener.onNoteClick(getAdapterPosition());
            mPreviousIndex = getAdapterPosition();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(R.layout.item_language, parent, false);
        return new LanguagesAdapterView.ViewHolder(messageView, this.onLanguageListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppCompatTextView textView = holder.label_languageTitle;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(
                    12,
                    30,
                    1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        } else {
            textView.setAutoSizeTextTypeUniformWithConfiguration(
                    12,
                    30,
                    1,
                    1);
        }
        textView.setText(languages.get(position));

        //color on item unselecting item
        holder.image.setVisibility(mPreviousIndex == position ? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public interface OnLanguageListener {
        void onNoteClick(int position);
    }

}