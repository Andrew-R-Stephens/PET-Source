
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreItemData;

import java.util.ArrayList;

public class ItemStoreData {

    private final ArrayList<ItemStoreGroupData> groupData;

    public ItemStoreData() {
        groupData = new ArrayList<>();
    }

    public void addGroup(ItemStoreGroupData groupItems) {
        this.groupData.add(groupItems);
    }

    public ItemStoreItemData getItemAt(int groupIndex, int itemIndex) {
        ItemStoreItemData item = null;

        ItemStoreGroupData group = groupData.get(groupIndex);
        if(group != null) {
            item = group.getItemDataAt(itemIndex);
        }

        return item;
    }

    public ItemStoreGroupData getGroupAt(int groupIndex) {
        return groupData.get(groupIndex);
    }

    public ArrayList<ItemStoreGroupData> getGroups() {
        return groupData;
    }

}