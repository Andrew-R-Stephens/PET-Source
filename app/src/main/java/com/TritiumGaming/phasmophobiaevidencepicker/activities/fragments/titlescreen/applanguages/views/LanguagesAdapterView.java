package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

public class LanguagesAdapterView extends RecyclerView.Adapter<LanguagesAdapterView.ViewHolder> {

    private final ArrayList<String> languages;
    private final OnLanguageListener onLanguageListener;

    public LanguagesAdapterView(ArrayList<String> languages, OnLanguageListener onLanguageListener) {
        this.languages = languages;
        this.onLanguageListener = onLanguageListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatTextView label_languageTitle;
        private final OnLanguageListener onLanguageListener;

        public ViewHolder(View view, OnLanguageListener onLanguageListener) {
            super(view);
            label_languageTitle = itemView.findViewById(R.id.textView_languageName);
            view.setOnClickListener(this);
            this.onLanguageListener = onLanguageListener;
        }

        @Override
        public void onClick(View v) {
            this.onLanguageListener.onNoteClick(getAdapterPosition());
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
            textView.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
        textView.setText(languages.get(position));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public interface OnLanguageListener {
        void onNoteClick(int position);
    }

}