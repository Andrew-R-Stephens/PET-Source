
package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreItemData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class ItemStoreData {

    @NonNull
    private final ArrayList<ItemStoreGroupData> groupData;

    private int groupIndexSelected = SelectionType.Undefined.value;
    private int itemIndexSelected = SelectionType.Undefined.value;

    public ItemStoreData() {
        groupData = new ArrayList<>();
    }

    public abstract void build(@NotNull Context context);

    public void addGroup(ItemStoreGroupData groupItems) {
        this.groupData.add(groupItems);
    }

    @Nullable
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

    @NonNull
    public ArrayList<ItemStoreGroupData> getGroups() {
        return groupData;
    }

    public int getGroupIndexSelected() {
        return groupIndexSelected;
    }

    public void setGroupIndexSelected(int groupIndexSelected) {
        this.groupIndexSelected = groupIndexSelected;
    }

    public int getItemIndexSelected() {
        return itemIndexSelected;
    }

    public void setItemIndexSelected(int itemIndexSelected) {
        this.itemIndexSelected = itemIndexSelected;
    }

    public void setSelectedItemAndGroupIndex(int groupIndexSelected, int itemIndexSelected) {
        setGroupIndexSelected(groupIndexSelected);
        setItemIndexSelected(itemIndexSelected);
    }

    public boolean isSelectedItemAndGroupIndex(int groupIndexSelected, int itemIndexSelected) {
        return (this.groupIndexSelected == groupIndexSelected) &&
                (this.itemIndexSelected == itemIndexSelected);
    }

    public void resetSelection() {
        setGroupIndexSelected(SelectionType.Undefined.value);
        setItemIndexSelected(SelectionType.Undefined.value);
    }

    enum SelectionType { Undefined(-1);
        public final int value;

        SelectionType(int value) {
            this.value = value;
        }
    }

}