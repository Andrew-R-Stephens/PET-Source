
package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.util.ArrayList;

public abstract class ItemStoreGroupData {

    private @StringRes int nameData;
    @NonNull
    private final ArrayList<ItemStoreItemData> itemData;
    private @DrawableRes int icon;

    public ItemStoreGroupData() {
        itemData = new ArrayList<>();
    }

    public void addItem(ItemStoreItemData item) {
        this.itemData.add(item);
    }

    public void setNameData(@StringRes int nameData) {
        this.nameData = nameData;
    }

    public @StringRes int getNameData() {
        return nameData;
    }

    public ItemStoreItemData getItemDataAt(int itemIndex) {
        return itemData.get(itemIndex);
    }

    public void setPaginationIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public @DrawableRes int getPaginationIcon() {
        return icon;
    }

    public int getSize() {
        return itemData.size();
    }

    @NonNull
    public ArrayList<Integer> getItemImages() {
        @DrawableRes ArrayList<Integer> images = new ArrayList<>();
        for (ItemStoreItemData data : itemData) {
            images.add(data.getImageData());
        }
        return images;
    }
}