
package com.TritiumGaming.phasmophobiaevidencepicker;

import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

import java.util.ArrayList;

public class ItemStoreEquipmentGroupData {

    private @StringRes int nameData;
    private @IntegerRes int buyCostData;
    private final ArrayList<ItemStoreEquipmentItemData> itemData;

    public ItemStoreEquipmentGroupData() {
        itemData = new ArrayList<>();
    }

    public void addItem(ItemStoreEquipmentItemData item) {
        this.itemData.add(item);
    }

    public void setNameData(@StringRes int nameData) {
        this.nameData = nameData;
    }

    public void setBuyCostData(@IntegerRes int buyCostData) {
        this.buyCostData = buyCostData;
    }

    public @StringRes int getNameData() {
        return nameData;
    }

    public @IntegerRes int getBuyCostData() {
        return buyCostData;
    }

    public ItemStoreEquipmentItemData getItemDataAt(int itemIndex) {
        return itemData.get(itemIndex);
    }

}