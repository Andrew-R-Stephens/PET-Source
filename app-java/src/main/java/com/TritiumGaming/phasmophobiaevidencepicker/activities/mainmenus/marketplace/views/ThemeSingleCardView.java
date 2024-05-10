package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.theme.MarketSingleTheme;
import com.google.android.material.card.MaterialCardView;

public class ThemeSingleCardView extends MaterialCardView {

    @Nullable
    private MarketSingleTheme theme = null;

    public ThemeSingleCardView(@NonNull Context context) {
        super(context, null);
    }

    public ThemeSingleCardView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ThemeSingleCardView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void init(@Nullable Context context) {
        inflate(context, R.layout.item_marketplace_theme, this);

        setLayoutParams(
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

        int strokeColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.backgroundColorOnBackground);

        setRadius(16);

        setStrokeColor(ColorUtils.interpolate(
                getResources().getColor(R.color.white),
                strokeColor,
                .25f));
        setStrokeWidth(3);

        setUseCompatPadding(true);
        setClipToPadding(false);
    }

    public void setPurchasable() {
        if(theme == null) {
            return;
        }

        if(theme.isUnlocked())
            setVisibility(GONE); {
        }

    }

    public void setCreditCost() {
        AppCompatTextView label_credits = findViewById(R.id.label_credits_cost);

        if(theme == null || label_credits == null) {
            return;
        }

        label_credits.setText(String.valueOf(theme.getBuyCredits()));
    }

    public long getCreditCost() {
        return theme.getBuyCredits();
    }

    public void setTheme(MarketSingleTheme theme) {
        this.theme = theme;

        setPurchasable();
        setCreditCost();
    }

    public void validate() {
        setPurchasable();
    }

    public void setBuyButtonListener(OnClickListener buyButtonListener) {
        View buyButton = findViewById(R.id.button_transactItem);

        if(buyButton != null) {
            buyButton.setOnClickListener(buyButtonListener);
        }
    }
}
