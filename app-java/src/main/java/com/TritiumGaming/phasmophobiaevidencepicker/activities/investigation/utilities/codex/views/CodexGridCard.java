package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.google.android.material.card.MaterialCardView;

public class CodexGridCard extends MaterialCardView {

    public CodexGridCard(Context context) {
        super(context);
        initView(context);
    }

    public CodexGridCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CodexGridCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        initView(context);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CodexGridCard);
        String text = a.getString(R.styleable.CodexGridCard_label_text);
        Drawable img = a.getDrawable(R.styleable.CodexGridCard_background_image);
        a.recycle();

        setText(text);
        setImage(img);
    }

    public void initView(Context context) {
        inflate(context, R.layout.item_codexmenu_option, this);
    }

    public void setText(String text) {
        AppCompatTextView textView = findViewById(R.id.label_itemName);
        textView.setText(text);
    }

    public void setImage(Drawable imageRes) {
        AppCompatImageView imgView = findViewById(R.id.image_item);
        imgView.setImageDrawable(imageRes);
    }
}