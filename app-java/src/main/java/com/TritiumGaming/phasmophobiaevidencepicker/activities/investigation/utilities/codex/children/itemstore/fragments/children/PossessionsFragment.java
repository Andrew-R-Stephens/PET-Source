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
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossessionItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossnsGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroup;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItem;

public class PossessionsFragment extends ItemStoreFragment {

    @SuppressLint("ResourceType")
    protected void buildStoreData() {
        if(getContext() == null) { return; }

        TypedArray typed_shop_list = getResources().obtainTypedArray(R.array.shop_cursedpossessions_array);
        //scrollViewPaginator.setRowCount(typed_shop_list.length());

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int possessionName;
            @DrawableRes int possessionIcon;

            ItemStorePossnsGroupData groupData = new ItemStorePossnsGroupData();

            TypedArray typed_shop =
                    getContext().getResources().obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            possessionName = typed_shop.getResourceId(0, 0);
            possessionIcon = typed_shop.getResourceId(1, 0);

            groupData.setNameData(possessionName);
            groupData.setPaginationIcon(possessionIcon);

            TypedArray typed_possession_image =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_possession_image.length(); j++) {
                groupData.addItem(new ItemStorePossessionItemData());
                @DrawableRes int value = typed_possession_image.getResourceId(j, 0);
                groupData.getItemDataAt(j).setImageData(value);

                //tierImages.add(value);
                groupData.getItemDataAt(j).setImageData(value);
            }
            typed_possession_image.recycle();

            TypedArray typed_equipment_flavortext =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(3, 0));
            for (int j = 0; j < typed_equipment_flavortext.length(); j++) {
                @StringRes int value = typed_equipment_flavortext.getResourceId(j, 0);
                groupData.getItemDataAt(j).setFlavorData(value);
            }
            typed_equipment_flavortext.recycle();

            TypedArray typed_equipment_infotext =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(4, 0));
            for (int j = 0; j < typed_equipment_infotext.length(); j++) {
                @StringRes int value = typed_equipment_infotext.getResourceId(j, 0);
                groupData.getItemDataAt(j).setInfoData(value);
            }
            typed_equipment_infotext.recycle();

            TypedArray typed_possessions_attributes =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(5, 0));
            for (int j = 0; j < typed_possessions_attributes.length(); j++) {
                @StringRes int value = typed_possessions_attributes.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).addAttribute(value);
            }
            typed_possessions_attributes.recycle();

            TypedArray typed_possessions_sanitydrain =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(6, 0));
            for (int j = 0; j < typed_possessions_sanitydrain.length(); j++) {
                @StringRes int value = typed_possessions_sanitydrain.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).setSanityDrainData(value);
            }
            typed_possessions_sanitydrain.recycle();

            TypedArray typed_possessions_drawchance =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(7, 0));
            for (int j = 0; j < typed_possessions_drawchance.length(); j++) {
                @StringRes int value = typed_possessions_drawchance.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).setDrawChance(value);
            }
            typed_possessions_drawchance.recycle();

            TypedArray typed_possessions_altnames =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(8, 0));
            for (int j = 0; j < typed_possessions_altnames.length(); j++) {
                @StringRes int value = typed_possessions_altnames.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).setAltName(value);
            }
            typed_possessions_altnames.recycle();

            typed_shop.recycle();

            storeData.addGroup(groupData);
        }
        typed_shop_list.recycle();

    }

    protected void setPageTitle(AppCompatTextView titleView) {
        titleView.setText(R.string.store_title_cursedpossessions);
    }

    protected void setDataViewLayout(View view) {
        ViewStub dv = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        dv.setLayoutResource(R.layout.layout_itemstore_itemdata_possessions);
        dataView = dv.inflate();
        dataView.setVisibility(View.INVISIBLE);
    }

    protected void createGroup(
            LinearLayoutCompat parent,
            ItemStoreGroupData group
    ) {

        if(getContext() == null) { return; }

        ItemStoreGroup itemStoreEquipmentGroup = new ItemStoreGroup(getContext(),
                (group.getSize() > 3 ?
                        R.layout.item_itemstore_itemgroup_long :
                        R.layout.item_itemstore_itemgroup));

        itemStoreEquipmentGroup.build(R.drawable.equipment_possession_item, group.getNameData(), group.getItemImages(), false);

        itemStoreEquipmentGroup.setVisibility(View.INVISIBLE);
        itemStoreEquipmentGroup.setAlpha(0);
        parent.addView(itemStoreEquipmentGroup);

    }

    @SuppressLint("ResourceType")
    protected void buildGroupViews(LinearLayoutCompat parent, GridLayout scrollViewPaginator) {
        if(getContext() == null) { return; }

        scrollViewPaginator.setRowCount(storeData.getGroups().size());

        for (ItemStoreGroupData group: storeData.getGroups()) {

            if(getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    addPaginatorIcon(scrollViewPaginator, group.getPaginationIcon());
                    createGroup(parent, group);
                });
            }
        }
    }

    protected void buildDataPopupView(View dataView, int groupIndex, int itemIndex) {
        if(getContext() == null) { return; }

        ItemStorePossnsGroupData groupData = (ItemStorePossnsGroupData) storeData.getGroupAt(groupIndex);
        ItemStorePossessionItemData itemData = (ItemStorePossessionItemData) groupData.getItemDataAt(itemIndex);

        AppCompatTextView itemNameView = dataView.findViewById(R.id.safehouse_shop_tool_label);
        AppCompatTextView flavortextView = dataView.findViewById(R.id.textview_itemshop_flavor);
        AppCompatTextView infotextView = dataView.findViewById(R.id.textview_itemshop_info);
        AppCompatTextView sanitydraintextView = dataView.findViewById(R.id.textview_sanitydrain);
        AppCompatTextView drawchancetextView = dataView.findViewById(R.id.textview_drawchance);
        AppCompatTextView altnametextView = dataView.findViewById(R.id.textview_itemshop_altname);
        AppCompatTextView attrtextView = dataView.findViewById(R.id.textview_itemshop_attributes);
        ItemStoreItem itemImageView = dataView.findViewById(R.id.itemStoreEquipmentItemData);

        itemNameView.setText(getString(groupData.getNameData()));
        flavortextView.setText(Html.fromHtml(getString(itemData.getFlavorData())));
        infotextView.setText(Html.fromHtml(getString(itemData.getInfoData())));
        try {
            sanitydraintextView.setText(Html.fromHtml(getString(itemData.getSanityDrainData())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            drawchancetextView.setText(Html.fromHtml(getString(itemData.getDrawChance())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            altnametextView.setVisibility(View.VISIBLE);
            altnametextView.setText(Html.fromHtml(getString(itemData.getAltName())));
        } catch (Resources.NotFoundException e) {
            altnametextView.setVisibility(View.GONE);
            e.printStackTrace();
        }

        try {
            String attrData = itemData.getAllAttributesAsFormattedHTML(getContext());
            if(attrData == null || attrData.length() > 0) {
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
        layerDrawable.setDrawableByLayerId(R.id.ic_type, ResourcesCompat.getDrawable(getResources(), itemData.getImageData(), getContext().getTheme()));
        layerDrawable.setLevel(0);
        itemImageView.invalidate();
    }

    @Override
    public void softReset() {

    }

    @Override
    protected void saveStates() {

    }

}
