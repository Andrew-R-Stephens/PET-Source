package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.google.android.material.card.MaterialCardView;

public class MarketplaceItem extends MaterialCardView {

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

        addView(inflate(context, R.layout.item_marketplace_item, null));

        int strokeColor = R.color.white;
        if (context != null) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.backgroundColorOnBackground, typedValue, true);
            strokeColor = typedValue.data;
        }

        setRadius(16);
        //setCardBackgroundColor(getResources().getColor(R.color.transparent));

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
}
