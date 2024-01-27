package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.bundle.MarketThemeBundle;
import com.google.android.material.card.MaterialCardView;

public class MarketplaceBundleThemeView extends MaterialCardView {

    private long creditCost = 0;

    public MarketplaceBundleThemeView(Context context) {
        super(context, null);
    }

    public MarketplaceBundleThemeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public MarketplaceBundleThemeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.item_marketplace_bundle, this);

        setLayoutParams(
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

        int strokeColor = R.color.white;
        if (context != null) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.backgroundColorOnBackground, typedValue, true);
            strokeColor = typedValue.data;
        }

        setRadius(16);

        setStrokeColor(ColorUtils.interpolate(
                getResources().getColor(R.color.white),
                strokeColor,
                .25f));
        setStrokeWidth(3);

        setUseCompatPadding(true);
        setClipToPadding(false);

        invalidate();
        requestLayout();
    }

    public void setPurchaseable(boolean isEnabled) {
        if(isEnabled) { return; }

        setVisibility(GONE);
    }

    public void setCreditCost(long creditCost) {
        this.creditCost = creditCost;

        AppCompatTextView label_credits = findViewById(R.id.label_credits_cost);
        label_credits.setText(String.valueOf(creditCost));
    }

    public long getCreditCost() {
        return creditCost;
    }

    public AppCompatButton getBuyButton() {
        return findViewById(R.id.button_transactItem);
    }

    public void setBundle(MarketThemeBundle bundleThemes) {
        AppCompatTextView title = findViewById(R.id.label_bundleTitle);
        title.setText(bundleThemes.getName());

        LinearLayout themesList = findViewById(R.id.themesList);

        for(CustomTheme theme: bundleThemes.getThemes()) {

            MarketplaceBundleCard card = new MarketplaceBundleCard(
                    new ContextThemeWrapper(getContext(), theme.getStyle()),
                    null, theme.getStyle());
            card.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, 1f));

            card.setObtained(theme.isUnlocked());

            themesList.addView(card);

        }
    }
}
