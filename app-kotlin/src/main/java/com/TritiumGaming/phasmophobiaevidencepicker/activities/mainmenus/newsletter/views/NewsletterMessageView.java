package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data.NewsletterMessagesData;

public class NewsletterMessageView extends ConstraintLayout {

    public NewsletterMessageView(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public NewsletterMessageView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public NewsletterMessageView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public void init(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        inflate(context, R.layout.item_newsletter_inbox, this);

        String title = "";
        @DrawableRes int icon = R.drawable.icon_codex;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsletterInboxView);
            title = a.getString(R.styleable.NewsletterInboxView_inbox_title);
            icon = a.getResourceId(R.styleable.NewsletterInboxView_inbox_icon, R.drawable.app_icon_sm);
            a.recycle();
        }

        setInboxTitle(title);
        setInboxIcon(icon);

        requestLayout();
    }

    public void setInboxIcon(int icon) {
        AppCompatImageView iconView = findViewById(R.id.inboxIcon);

        if(iconView != null) {
            iconView.setImageResource(icon);
        }
    }

    public void setInboxTitle(String title) {
        AppCompatTextView titleView = findViewById(R.id.inboxTitle);

        if(titleView != null) {
            titleView.setText(title);
            titleView.setSelected(true);
        }
    }

    public void initNotify(@NonNull Context context, @Nullable NewsletterMessagesData inboxData) {
        AppCompatImageView notifyView = findViewById(R.id.notifyIcon);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.notifyblink);
        if (inboxData != null && inboxData.compareDates()) {
            notifyView.setAlpha(0.9f);
            notifyView.startAnimation(animation);
        }
    }
}

