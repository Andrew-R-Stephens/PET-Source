package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.LayerDrawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.compose.ui.platform.ComposeView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossessionItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossnsGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ItemStoreViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreComposablesKt;

public class EquipmentDataView extends ItemStoreDataView {

    public EquipmentDataView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public EquipmentDataView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EquipmentDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public EquipmentDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        super.init();
        layoutResId = R.layout.layout_itemstore_itemdata_equipment;
        inflate(context, layoutResId, this);
    }

    public void buildData(ItemStoreViewModel itemStoreViewModel, int groupIndex, int itemIndex) {

        ItemStorePossnsGroupData groupData = (ItemStorePossnsGroupData) itemStoreViewModel.getPossessionData().getGroupAt(groupIndex);
        ItemStorePossessionItemData itemData = (ItemStorePossessionItemData) groupData.getItemDataAt(itemIndex);

        AppCompatTextView itemNameView = findViewById(R.id.safehouse_shop_tool_label);
        AppCompatTextView flavortextView = findViewById(R.id.textview_itemshop_flavor);
        AppCompatTextView infotextView = findViewById(R.id.textview_itemshop_info);
        AppCompatTextView sanitydraintextView = findViewById(R.id.textview_sanitydrain);
        AppCompatTextView drawchancetextView = findViewById(R.id.textview_drawchance);
        AppCompatTextView altnametextView = findViewById(R.id.textview_itemshop_altname);
        AppCompatTextView attrtextView = findViewById(R.id.textview_itemshop_attributes);
        //ItemStoreItem itemImageView = findViewById(R.id.itemStoreEquipmentItemData);
        ComposeView itemImageView = findViewById(R.id.itemStoreEquipmentItemData);

        try {
            itemNameView.setText(getResources().getString(groupData.getNameData()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            flavortextView.setText(Html.fromHtml(getResources().getString(itemData.getFlavorData())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            infotextView.setText(Html.fromHtml(getResources().getString(itemData.getInfoData())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            sanitydraintextView.setText(Html.fromHtml(getResources().getString(itemData.getSanityDrainData())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            drawchancetextView.setText(Html.fromHtml(getResources().getString(itemData.getDrawChance())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        altnametextView.setVisibility(View.VISIBLE);
        try {
            altnametextView.setText(Html.fromHtml(getResources().getString(itemData.getAltName())));
        } catch (Resources.NotFoundException e) {
            altnametextView.setVisibility(View.GONE);
            e.printStackTrace();
        }

        try {
            String attrData = itemData.getAllAttributesAsFormattedHTML(getContext());
            if (!attrData.isEmpty()) {
                attrtextView.setVisibility(View.GONE);
            } else {
                attrtextView.setVisibility(View.VISIBLE);

                if (getContext() != null) {
                    attrtextView.setText(attrData);
                }
            }

        } catch (Resources.NotFoundException e) {
            attrtextView.setVisibility(View.GONE);
            e.printStackTrace();
        }

        attrtextView.setText(Html.fromHtml(itemData.getAllAttributesAsFormattedHTML(getContext())));

        ItemStoreComposablesKt.setEquipmentSimple(itemImageView, itemData.getImageData(), itemIndex+1 );
    }
}
