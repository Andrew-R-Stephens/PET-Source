
package com.TritiumGaming.phasmophobiaevidencepicker;

import java.util.ArrayList;

public class ItemStoreEquipmentStoreData {

    private ArrayList<ItemStoreEquipmentGroupData> groupData;

    public ItemStoreEquipmentStoreData() {
        groupData = new ArrayList<>();
    }

    public void addGroup(ItemStoreEquipmentGroupData groupItems) {
        this.groupData.add(groupItems);
    }

    public ItemStoreEquipmentItemData getItemAt(int groupIndex, int itemIndex) {
        ItemStoreEquipmentItemData item = null;

        ItemStoreEquipmentGroupData group = groupData.get(groupIndex);
        if(group != null) {
            item = group.getItemDataAt(itemIndex);
        }

        return item;
    }

}