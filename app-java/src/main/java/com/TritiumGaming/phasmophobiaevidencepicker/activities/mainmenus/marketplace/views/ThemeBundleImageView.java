package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.google.android.material.card.MaterialCardView;

public class ThemeBundleImageView extends MaterialCardView {

    private CustomTheme theme;

    public ThemeBundleImageView(@NonNull Context context) {
        super(context, null);

        init(context);
    }

    public ThemeBundleImageView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ThemeBundleImageView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.item_marketplace_bundle_image, this);

        setCardBackgroundColor(getResources().getColor(R.color.transparent));
        setElevation(0);

        setForegroundGravity(Gravity.CENTER);

        setClipToPadding(false);
        setSelected(true);
    }

    public void setTheme(CustomTheme theme) {
        this.theme = theme;

        setObtainedIconVisibility();
    }

    public void setObtainedIconVisibility() {
        AppCompatImageView lockedState = findViewById(R.id.image_obtained);

        if(this.theme != null && lockedState != null) {
            lockedState.setVisibility(this.theme.isUnlocked() ? VISIBLE : GONE);
        }
    }

    public void validate() {
        setObtainedIconVisibility();
    }
}