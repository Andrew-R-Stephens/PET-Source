package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.settings.ThemeModel;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.firestore.theme.bundle.MarketThemeBundleModel;
import com.google.android.material.card.MaterialCardView;

public class ThemeBundleCardView extends MaterialCardView {

    @Nullable
    private MarketThemeBundleModel bundle = null;

    public ThemeBundleCardView(@NonNull Context context) {
        super(context, null);
    }

    public ThemeBundleCardView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ThemeBundleCardView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void init(@Nullable Context context) {
        inflate(context, R.layout.item_marketplace_bundle, this);

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

    public long getCreditCost() {
        if(bundle == null) {
            return 0;
        }

        return bundle.getDiscountedBuyCredits();
    }

    public void setBundle(@Nullable MarketThemeBundleModel bundle) {
        this.bundle = bundle;

        if(bundle == null) {
            return;
        }

        AppCompatTextView titleView = findViewById(R.id.label_bundleTitle);
        if(titleView != null) {
            titleView.setText(this.bundle.getName());
        }

        AppCompatTextView costView = findViewById(R.id.label_credits_cost);
        if(costView != null) {
            //costView.setText(String.valueOf(this.bundle.getBuyCredits()));
            costView.setText(String.valueOf(this.bundle.getDiscountedBuyCredits()));
        }

        buildThemes();
    }

    public void buildThemes() {
        LinearLayout themesList = findViewById(R.id.themesList);

        if(bundle != null) {
            for (ThemeModel theme : bundle.getThemes()) {

                ThemeBundleImageView card = new ThemeBundleImageView(
                        new ContextThemeWrapper(
                                getContext(),
                                theme.getStyle()),
                        null,
                        theme.getStyle());
                card.setTheme(theme);

                card.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT, 1f));

                themesList.addView(card);
            }
        }
    }

    public void validate() {
        if(!validateVisibility()) {
            return;
        }

        validateThemesList();
    }

    private void validateThemesList() {
        LinearLayout themesList = findViewById(R.id.themesList);
        if(bundle != null) {
            for (int i = 0; i < themesList.getChildCount(); i++) {

                View child = themesList.getChildAt(i);
                if(child instanceof ThemeBundleImageView bundleCard) {
                    bundleCard.validate();
                }
            }

            setCreditCost();
        }
    }

    private boolean validateVisibility() {
        boolean isAvailable = bundle.getLockedItemCount() > 1;

        setVisibility(isAvailable ? VISIBLE : GONE);

        return isAvailable;
    }

    public void setCreditCost() {
        AppCompatTextView label_credits = findViewById(R.id.label_credits_cost);

        if(bundle == null) {
            return;
        }

        label_credits.setText(String.valueOf(bundle.getDiscountedBuyCredits()));
    }

    public void setBuyButtonListener(OnClickListener buyButtonListener) {
        View buyButton = findViewById(R.id.button_transactItem);

        if(buyButton != null) {
            buyButton.setOnClickListener(buyButtonListener);
        }
    }
}
