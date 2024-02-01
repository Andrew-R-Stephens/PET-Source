package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace.billing;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.billable.MarketplaceMtxItem;
import com.google.android.material.card.MaterialCardView;

public class MarketplaceMtxView extends MaterialCardView {

    private MarketplaceMtxItem billableItem;

    public MarketplaceMtxView(Context context) {
        this(context, null);

        init(context);
    }

    public MarketplaceMtxView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public MarketplaceMtxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.item_marketplace_micro, this);

        setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        int strokeColor = R.color.white;
        if (context != null) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.textColorBodyEmphasis, typedValue, true);
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
    }

    public void setBillableItem(MarketplaceMtxItem billableItem) {
        this.billableItem = billableItem;

        setBannerLabel();
        setTitle();
        setDescription();
        setCreditCost();
    }

    public void setBannerLabel() {
        AppCompatTextView label_title = findViewById(R.id.label_mtx_title);
        if(label_title == null) {
            return;
        }

        if(billableItem == null) {
            return;
        }

        label_title.setText(
                billableItem.getName());
    }

    public void setTitle() {
        AppCompatTextView label_montaryValue = findViewById(R.id.label_mtx_content);
        if(label_montaryValue == null) {
            return;
        }

        if(billableItem == null || billableItem.getName() == null) {
            return;
        }

        label_montaryValue.setText(
                billableItem.getName());
    }

    public void setDescription() {
        AppCompatTextView label_montaryValue = findViewById(R.id.label_mtx_description);
        if(label_montaryValue == null) {
            return;
        }

        if(billableItem == null || billableItem.getDescription() == null) {
            return;
        }

        label_montaryValue.setText(
                billableItem.getDescription());
    }

    public void setCreditCost() {
        AppCompatTextView label_montaryValue = findViewById(R.id.label_monetary_value);
        if(label_montaryValue == null) {
            return;
        }

        if(billableItem == null || billableItem.getPurchaseAmount() == null) {
            return;
        }

        label_montaryValue.setText(
                billableItem.getPurchaseAmount());
    }

    public void validate() {
        setCreditCost();
    }

    public void setBuyButtonListener(OnClickListener buyButtonListener) {
        View buyButton = findViewById(R.id.button_transactItem);

        if(buyButton != null) {
            buyButton.setOnClickListener(buyButtonListener);
        }
    }

    public MarketplaceMtxItem getBillableItem() {
        return billableItem;
    }
}
