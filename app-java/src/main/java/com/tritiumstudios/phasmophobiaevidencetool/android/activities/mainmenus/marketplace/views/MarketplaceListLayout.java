package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.marketplace.views;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.tritiumstudios.phasmophobiaevidencetool.R;

public class MarketplaceListLayout extends LinearLayout {

    public MarketplaceListLayout(Context context) {
        super(context);

        init();
    }

    public MarketplaceListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public MarketplaceListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public MarketplaceListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    public void init() {
        inflate(getContext(), R.layout.layout_marketplace_grouplabel, this);

        setLayoutTransition(new LayoutTransition());

        setOrientation(LinearLayout.VERTICAL);
    }

    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void setLabel(String text) {
        AppCompatTextView label = findViewById(R.id.label);
        if(label != null) {
            label.setText(text);
        }
    }

    public void showLabel(int visibility) {
        AppCompatTextView label = findViewById(R.id.label);
        if(label != null) {
            label.setVisibility(visibility);
        }
    }

    public void validateItems() {
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof ThemeSingleCardView singleThemeView) {
                singleThemeView.validate();
            }
            else if(child instanceof ThemeBundleCardView bundleThemeView) {
                bundleThemeView.validate();
            }
        }
    }
}
