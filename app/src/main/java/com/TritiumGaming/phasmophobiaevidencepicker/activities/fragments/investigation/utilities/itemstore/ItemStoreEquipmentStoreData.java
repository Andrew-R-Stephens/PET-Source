
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.itemstore;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public class ItemStoreEquipmentStoreData {

    private final ArrayList<ItemStoreEquipmentGroupData> groupData;

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

    public ItemStoreEquipmentGroupData getGroupAt(int groupIndex) {
        return groupData.get(groupIndex);
    }

    public ArrayList<ItemStoreEquipmentGroupData> getGroups() {
        return groupData;
    }

}