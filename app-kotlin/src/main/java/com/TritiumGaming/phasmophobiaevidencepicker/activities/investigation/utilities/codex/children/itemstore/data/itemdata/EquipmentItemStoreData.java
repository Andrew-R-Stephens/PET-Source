package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.ItemStoreData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentItemData;

import org.jetbrains.annotations.NotNull;

public class EquipmentItemStoreData extends ItemStoreData {

    @SuppressLint({"ResourceType"})
    public void build(@NotNull Context context) {

        final Resources resources = context.getResources();

        TypedArray typed_shop_list =
                resources.obtainTypedArray(R.array.shop_equipment_array);

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int equipmentName;
            @IntegerRes int buyCostData;
            @DrawableRes int equipmentIcon;

            ItemStoreEquipmentGroupData groupData = new ItemStoreEquipmentGroupData();

            TypedArray typed_shop =
                    resources.obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            equipmentName = typed_shop.getResourceId(0, 0);
            equipmentIcon = typed_shop.getResourceId(1, 0);
            buyCostData = typed_shop.getResourceId(6, 0);

            groupData.setNameData(equipmentName);
            groupData.setPaginationIcon(equipmentIcon);
            groupData.setBuyCostData(buyCostData);

            TypedArray typed_equipment_image =
                    resources.obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_equipment_image.length(); j++) {
                ItemStoreEquipmentItemData itemData = new ItemStoreEquipmentItemData();
                groupData.addItem(itemData);
                @DrawableRes int value = typed_equipment_image.getResourceId(j, 0);
                groupData.getItemDataAt(j).setImageData(value);

                //tierImages.add(value);
                groupData.getItemDataAt(j).setImageData(value);
            }
            typed_equipment_image.recycle();

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

            TypedArray typed_equipment_attributes =
                    resources.obtainTypedArray(typed_shop.getResourceId(5, 0));
            for (int j = 0; j < typed_equipment_attributes.length(); j++) {

                TypedArray typed_equipment_attributes_positive =
                        resources.obtainTypedArray(typed_equipment_attributes.getResourceId(j, 0));
                TypedArray typed_equipment_attributes_positive_list =
                        resources.obtainTypedArray(typed_equipment_attributes_positive.getResourceId(0, 0));
                for (int l = 0; l < typed_equipment_attributes_positive_list.length(); l++) {
                    @StringRes int value = typed_equipment_attributes_positive_list.getResourceId(l, 0);
                    ((ItemStoreEquipmentItemData)groupData.getItemDataAt(j)).addPositiveAttribute(value);
                }
                typed_equipment_attributes_positive_list.recycle();
                typed_equipment_attributes_positive.recycle();

                TypedArray typed_equipment_attributes_negative =
                        resources.obtainTypedArray(typed_equipment_attributes.getResourceId(j, 0));
                TypedArray typed_equipment_attributes_negative_list =
                        resources.obtainTypedArray(typed_equipment_attributes_negative.getResourceId(1, 0));
                for (int l = 0; l < typed_equipment_attributes_negative_list.length(); l++) {
                    @StringRes int value = typed_equipment_attributes_negative_list.getResourceId(l, 0);
                    ((ItemStoreEquipmentItemData)groupData.getItemDataAt(j)).addNegativeAttribute(value);
                }
                typed_equipment_attributes_negative_list.recycle();
                typed_equipment_attributes_negative.recycle();
            }
            typed_equipment_attributes.recycle();

            TypedArray typed_equipment_upgradelevel =
                    resources.obtainTypedArray(typed_shop.getResourceId(7, 0));
            for (int j = 0; j < typed_equipment_upgradelevel.length(); j++) {
                @IntegerRes int value = typed_equipment_upgradelevel.getResourceId(j, 0);
                ((ItemStoreEquipmentItemData)groupData.getItemDataAt(j)).setUpgradeLevel(value);
            }
            typed_equipment_upgradelevel.recycle();

            TypedArray typed_equipment_upgradecost =
                    resources.obtainTypedArray(typed_shop.getResourceId(8, 0));
            for (int j = 0; j < typed_equipment_upgradecost.length(); j++) {
                @IntegerRes int value = typed_equipment_upgradecost.getResourceId(j, 0);
                ((ItemStoreEquipmentItemData)groupData.getItemDataAt(j)).setUpgradeCostData(value);
            }
            typed_equipment_upgradecost.recycle();

            typed_shop.recycle();

            super.addGroup(groupData);
        }
        typed_shop_list.recycle();

    }

}
