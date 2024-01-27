package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.google.android.material.card.MaterialCardView;

public class MarketplaceSingleThemeView extends MaterialCardView {

    private long creditCost = 0;

    public MarketplaceSingleThemeView(Context context) {
        super(context, null);
    }

    public MarketplaceSingleThemeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context/*, attrs*/);
    }

    public MarketplaceSingleThemeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context/*, attrs*/);
    }

    public void init(Context context/*, AttributeSet attrs*/) {
        inflate(context, R.layout.item_marketplace_theme, this);

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
}
