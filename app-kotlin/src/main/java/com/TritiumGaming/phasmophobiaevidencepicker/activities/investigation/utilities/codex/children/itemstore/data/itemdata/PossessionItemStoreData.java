package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.ItemStoreData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossessionItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossnsGroupData;

import org.jetbrains.annotations.NotNull;

public class PossessionItemStoreData extends ItemStoreData {

    @SuppressLint("ResourceType")
    public void build(@NotNull Context context) {

        final Resources resources = context.getResources();

        TypedArray typed_shop_list =
                resources.obtainTypedArray(R.array.shop_cursedpossessions_array);

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int possessionName;
            @DrawableRes int possessionIcon;

            ItemStorePossnsGroupData groupData = new ItemStorePossnsGroupData();

            TypedArray typed_shop =
                    resources.obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            possessionName = typed_shop.getResourceId(0, 0);
            possessionIcon = typed_shop.getResourceId(1, 0);

            groupData.setNameData(possessionName);
            groupData.setPaginationIcon(possessionIcon);

            TypedArray typed_possession_image =
                    resources.obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_possession_image.length(); j++) {
                groupData.addItem(new ItemStorePossessionItemData());
                @DrawableRes int value = typed_possession_image.getResourceId(j, 0);
                groupData.getItemDataAt(j).setImageData(value);

                //tierImages.add(value);
                groupData.getItemDataAt(j).setImageData(value);
            }
            typed_possession_image.recycle();

            TypedArray typed_equipment_flavortext =
                    resources.obtainTypedArray(typed_shop.getResourceId(3, 0));
            for (int j = 0; j < typed_equipment_flavortext.length(); j++) {
                @StringRes int value = typed_equipment_flavortext.getResourceId(j, 0);
                groupData.getItemDataAt(j).setFlavorData(value);
            }
            typed_equipment_flavortext.recycle();

            TypedArray typed_equipment_infotext =
                    resources.obtainTypedArray(typed_shop.getResourceId(4, 0));
            for (int j = 0; j < typed_equipment_infotext.length(); j++) {
                @StringRes int value = typed_equipment_infotext.getResourceId(j, 0);
                groupData.getItemDataAt(j).setInfoData(value);
            }
            typed_equipment_infotext.recycle();

            TypedArray typed_possessions_attributes =
                    resources.obtainTypedArray(typed_shop.getResourceId(5, 0));
            for (int j = 0; j < typed_possessions_attributes.length(); j++) {
                @StringRes int value = typed_possessions_attributes.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).addAttribute(value);
            }
            typed_possessions_attributes.recycle();

            TypedArray typed_possessions_sanitydrain =
                    resources.obtainTypedArray(typed_shop.getResourceId(6, 0));
            for (int j = 0; j < typed_possessions_sanitydrain.length(); j++) {
                @StringRes int value = typed_possessions_sanitydrain.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).setSanityDrainData(value);
            }
            typed_possessions_sanitydrain.recycle();

            TypedArray typed_possessions_drawchance =
                    resources.obtainTypedArray(typed_shop.getResourceId(7, 0));
            for (int j = 0; j < typed_possessions_drawchance.length(); j++) {
                @StringRes int value = typed_possessions_drawchance.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).setDrawChance(value);
            }
            typed_possessions_drawchance.recycle();

            TypedArray typed_possessions_altnames =
                    resources.obtainTypedArray(typed_shop.getResourceId(8, 0));
            for (int j = 0; j < typed_possessions_altnames.length(); j++) {
                @StringRes int value = typed_possessions_altnames.getResourceId(j, 0);
                ((ItemStorePossessionItemData)groupData.getItemDataAt(j)).setAltName(value);
            }
            typed_possessions_altnames.recycle();

            typed_shop.recycle();

            super.addGroup(groupData);
        }
        typed_shop_list.recycle();

    }


}
