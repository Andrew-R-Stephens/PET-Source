package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.google.android.material.card.MaterialCardView;

public class MarketplaceBundleCard extends MaterialCardView {

    public MarketplaceBundleCard(Context context) {
        super(context, null);

        init(context);
    }

    public MarketplaceBundleCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context/*, attrs*/);
    }

    public MarketplaceBundleCard(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setObtained(boolean isUnlocked) {
        AppCompatImageView obtained = findViewById(R.id.image_obtained);

        if(obtained != null) {
            obtained.setVisibility(isUnlocked ? VISIBLE : GONE);
        }
    }
}