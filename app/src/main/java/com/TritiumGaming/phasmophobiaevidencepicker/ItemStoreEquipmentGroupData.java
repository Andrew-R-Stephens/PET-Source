
package com.TritiumGaming.phasmophobiaevidencepicker;

import java.util.ArrayList;

public class ItemStoreEquipmentGroupData {

    private ArrayList<ItemStoreEquipmentItemData> itemData;

    public ItemStoreEquipmentGroupData() {
        itemData = new ArrayList<>();
    }

    public void addItem(ItemStoreEquipmentItemData item) {
        this.itemData.add(item);
    }

    public ItemStoreEquipmentItemData getItemDataAt(int itemIndex) {
        return itemData.get(itemIndex);
    }

}