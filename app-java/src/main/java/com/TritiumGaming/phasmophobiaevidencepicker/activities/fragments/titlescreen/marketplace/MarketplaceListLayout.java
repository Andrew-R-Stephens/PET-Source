package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;

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
        inflate(getContext(), R.layout.layout_marketplace_grouplist, this);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutTransition(new LayoutTransition());
        setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public void setLabel(String text) {
        AppCompatTextView label = (AppCompatTextView)findViewById(R.id.label);
        label.setText(text);
    }

    public void showLabel(int visibility) {
        AppCompatTextView label = (AppCompatTextView)findViewById(R.id.label);
        label.setVisibility(visibility);
    }
}
