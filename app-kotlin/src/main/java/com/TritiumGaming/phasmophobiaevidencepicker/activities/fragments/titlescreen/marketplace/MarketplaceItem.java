package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import androidx.annotation.IntegerRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.google.android.material.card.MaterialCardView;

public class MarketplaceItem extends MaterialCardView {

    private long creditCost = 0;

    public MarketplaceItem(Context context) {
        super(context, null);
    }

    public MarketplaceItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public MarketplaceItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        Log.d("MPView", "Inflating");

        inflate(context, R.layout.item_marketplace_item, this);

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

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MarketplaceItem,
                0, 0);

        @IntegerRes int themeRes =
                a.getType(R.styleable.MarketplaceItem_enforcedTheme);
        Log.d("Themetype", themeRes + "");

        try {
            invalidate();
            requestLayout();
        } finally {
            a.recycle();
        }
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
