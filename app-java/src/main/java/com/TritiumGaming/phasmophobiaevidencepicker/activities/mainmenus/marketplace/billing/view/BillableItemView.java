package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.billing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.billable.MarketplaceMtxItemModel;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils;
import com.google.android.material.card.MaterialCardView;

public class BillableItemView extends MaterialCardView {

    private MarketplaceMtxItemModel billableItem;

    public BillableItemView(@NonNull Context context) {
        this(context, null);

        init(context);
    }

    public BillableItemView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public BillableItemView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void init(@Nullable Context context) {
        inflate(context, R.layout.item_marketplace_micro, this);

        setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        int strokeColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorBodyEmphasis);

        setRadius(16);

        setStrokeColor(ColorUtils.interpolate(
                getResources().getColor(R.color.white),
                strokeColor,
                .25f));
        setStrokeWidth(3);

        setUseCompatPadding(true);
        setClipToPadding(false);
    }

    public void setBillableItem(MarketplaceMtxItemModel billableItem) {
        this.billableItem = billableItem;

        update();
    }

    private void update() {
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

        label_title.setText(billableItem.getName());
    }

    public void setTitle() {
        AppCompatTextView label_montaryValue = findViewById(R.id.label_mtx_content);
        if(label_montaryValue == null) {
            return;
        }

        if(billableItem == null) {
            return;
        } else {
            billableItem.getName();
        }

        label_montaryValue.setText(
                billableItem.getName());
    }

    public void setDescription() {
        AppCompatTextView label_montaryValue = findViewById(R.id.label_mtx_description);
        if(label_montaryValue == null) {
            return;
        }

        if(billableItem == null) {
            return;
        } else {
            billableItem.getDescription();
        }

        label_montaryValue.setText(
                billableItem.getDescription());
    }

    public void setCreditCost() {
        AppCompatTextView label_montaryValue = findViewById(R.id.label_monetary_value);
        if(label_montaryValue == null) {
            return;
        }

        if(billableItem == null) {
            return;
        } else {
            billableItem.getPurchaseAmount();
        }

        label_montaryValue.setText(
                billableItem.getPurchaseAmount());
    }

    public void setBuyButtonListener(OnClickListener buyButtonListener) {
        View buyButton = findViewById(R.id.button_transactItem);

        if(buyButton != null) {
            buyButton.setOnClickListener(buyButtonListener);
        }
    }

    public MarketplaceMtxItemModel getBillableItem() {
        return billableItem;
    }
}
