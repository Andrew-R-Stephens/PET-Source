package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.LayerDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore.ItemStoreGroupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore.possessions.ItemStorePossessionItemModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore.possessions.ItemStorePossnsGroupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView;

public class PossessionsFragment extends ItemStoreFragment {

    @SuppressLint("ResourceType")
    protected void buildStoreData() {

        TypedArray typed_shop_list = getResources().obtainTypedArray(R.array.shop_cursedpossessions_array);
        //scrollViewPaginator.setRowCount(typed_shop_list.length());

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int possessionName;
            @DrawableRes int possessionIcon;

            ItemStorePossnsGroupModel groupData = new ItemStorePossnsGroupModel();

            TypedArray typed_shop =
                    getResources().obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            possessionName = typed_shop.getResourceId(0, 0);
            possessionIcon = typed_shop.getResourceId(1, 0);

            groupData.nameData = possessionName;
            groupData.setPaginationIcon(possessionIcon);

            TypedArray typed_possession_image =
                    getResources().obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_possession_image.length(); j++) {
                groupData.addItem(new ItemStorePossessionItemModel());
                @DrawableRes int value = typed_possession_image.getResourceId(j, 0);
                groupData.getItemDataAt(j).imageData = value;

                //tierImages.add(value);
                groupData.getItemDataAt(j).imageData = value;
            }
            typed_possession_image.recycle();

            TypedArray typed_equipment_flavortext =
                    getResources().obtainTypedArray(typed_shop.getResourceId(3, 0));
            for (int j = 0; j < typed_equipment_flavortext.length(); j++) {
                @StringRes int value = typed_equipment_flavortext.getResourceId(j, 0);
                groupData.getItemDataAt(j).flavorData = value;
            }
            typed_equipment_flavortext.recycle();

            TypedArray typed_equipment_infotext =
                    getResources().obtainTypedArray(typed_shop.getResourceId(4, 0));
            for (int j = 0; j < typed_equipment_infotext.length(); j++) {
                @StringRes int value = typed_equipment_infotext.getResourceId(j, 0);
                groupData.getItemDataAt(j).infoData = value;
            }
            typed_equipment_infotext.recycle();

            TypedArray typed_possessions_attributes =
                    getResources().obtainTypedArray(typed_shop.getResourceId(5, 0));
            for (int j = 0; j < typed_possessions_attributes.length(); j++) {
                @StringRes int value = typed_possessions_attributes.getResourceId(j, 0);
                ((ItemStorePossessionItemModel)groupData.getItemDataAt(j)).addAttribute(value);
            }
            typed_possessions_attributes.recycle();

            TypedArray typed_possessions_sanitydrain =
                    getResources().obtainTypedArray(typed_shop.getResourceId(6, 0));
            for (int j = 0; j < typed_possessions_sanitydrain.length(); j++) {
                @StringRes int value = typed_possessions_sanitydrain.getResourceId(j, 0);
                ((ItemStorePossessionItemModel)groupData.getItemDataAt(j)).setSanityDrainData(value);
            }
            typed_possessions_sanitydrain.recycle();

            TypedArray typed_possessions_drawchance =
                    getResources().obtainTypedArray(typed_shop.getResourceId(7, 0));
            for (int j = 0; j < typed_possessions_drawchance.length(); j++) {
                @StringRes int value = typed_possessions_drawchance.getResourceId(j, 0);
                ((ItemStorePossessionItemModel)groupData.getItemDataAt(j)).drawChance = value;
            }
            typed_possessions_drawchance.recycle();

            TypedArray typed_possessions_altnames =
                    getResources().obtainTypedArray(typed_shop.getResourceId(8, 0));
            for (int j = 0; j < typed_possessions_altnames.length(); j++) {
                @StringRes int value = typed_possessions_altnames.getResourceId(j, 0);
                ((ItemStorePossessionItemModel)groupData.getItemDataAt(j)).altName = value;
            }
            typed_possessions_altnames.recycle();

            typed_shop.recycle();

            storeData.addGroup(groupData);
        }
        typed_shop_list.recycle();

    }

    protected void setPageTitle(@NonNull AppCompatTextView titleView) {
        titleView.setText(R.string.store_title_cursedpossessions);
    }

    protected void setDataViewLayout(@NonNull View view) {
        ViewStub dv = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        dv.setLayoutResource(R.layout.layout_itemstore_itemdata_possessions);
        dataView = dv.inflate();
        dataView.setVisibility(View.INVISIBLE);
    }

    protected void createGroup(
            @NonNull LinearLayoutCompat parent,
            @NonNull ItemStoreGroupModel group
    ) {
        ItemStoreGroupListView itemStoreGroupList = new ItemStoreGroupListView(requireContext());
        itemStoreGroupList.build(R.drawable.equipment_possession_item, group);
        parent.addView(itemStoreGroupList);
    }

    @SuppressLint("ResourceType")
    protected void buildGroupViews(@NonNull LinearLayoutCompat parent, @NonNull GridLayout scrollViewPaginator) {

        scrollViewPaginator.setRowCount(storeData.getGroups().size());

        for (ItemStoreGroupModel group: storeData.getGroups()) {

            try {
                requireActivity().runOnUiThread(() -> {
                    addPaginatorIcon(scrollViewPaginator, group.getPaginationIcon());
                    createGroup(parent, group);
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    protected void buildDataPopupView(@NonNull View dataView, int groupIndex, int itemIndex) {

        ItemStorePossnsGroupModel groupData = (ItemStorePossnsGroupModel) storeData.getGroupAt(groupIndex);
        ItemStorePossessionItemModel itemData = (ItemStorePossessionItemModel) groupData.getItemDataAt(itemIndex);

        AppCompatTextView itemNameView = dataView.findViewById(R.id.safehouse_shop_tool_label);
        AppCompatTextView flavortextView = dataView.findViewById(R.id.textview_itemshop_flavor);
        AppCompatTextView infotextView = dataView.findViewById(R.id.textview_itemshop_info);
        AppCompatTextView sanitydraintextView = dataView.findViewById(R.id.textview_sanitydrain);
        AppCompatTextView drawchancetextView = dataView.findViewById(R.id.textview_drawchance);
        AppCompatTextView altnametextView = dataView.findViewById(R.id.textview_itemshop_altname);
        AppCompatTextView attrtextView = dataView.findViewById(R.id.textview_itemshop_attributes);
        ItemStoreItemView itemImageView = dataView.findViewById(R.id.itemStoreEquipmentItemData);

        itemNameView.setText(getString(groupData.nameData));
        flavortextView.setText(Html.fromHtml(getString(itemData.flavorData)));
        infotextView.setText(Html.fromHtml(getString(itemData.infoData)));
        try {
            sanitydraintextView.setText(Html.fromHtml(getString(itemData.getSanityDrainData())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            drawchancetextView.setText(Html.fromHtml(getString(itemData.drawChance)));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            altnametextView.setVisibility(View.VISIBLE);
            altnametextView.setText(Html.fromHtml(getString(itemData.altName)));
        } catch (Resources.NotFoundException e) {
            altnametextView.setVisibility(View.GONE);
            e.printStackTrace();
        }

        try {
            String attrData = itemData.getAllAttributesAsFormattedHTML(requireContext());
            if(!attrData.isEmpty()) {
                attrtextView.setVisibility(View.GONE);
            } else {
                attrtextView.setVisibility(View.VISIBLE);

                if(getContext() != null) {
                    attrtextView.setText(attrData);
                }
            }

        } catch (Resources.NotFoundException e) {
            attrtextView.setVisibility(View.GONE);
            e.printStackTrace();
        }

        if(getContext() != null) {
            attrtextView.setText(Html.fromHtml(itemData.getAllAttributesAsFormattedHTML(getContext())));
        }

        LayerDrawable layerDrawable = (LayerDrawable) (itemImageView.getDrawable());
        layerDrawable.setDrawableByLayerId(R.id.ic_type, ResourcesCompat.getDrawable(
                getResources(), itemData.imageData, getContext().getTheme()));
        layerDrawable.setLevel(0);
        itemImageView.invalidate();
    }

    @Override
    public void reset() {

    }

    @Override
    protected void saveStates() {

    }

}
