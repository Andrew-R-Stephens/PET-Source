package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.ItemStoreData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.EquipmentItemStoreData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.PossessionItemStoreData;

import org.jetbrains.annotations.NotNull;

/**
 * ObjectivesViewModel class
 *
 * @author TritiumGamingStudios
 */
public class ItemStoreViewModel extends ViewModel {

    private ItemStoreData currentStore;

    private ItemStoreData equipmentData, possessionData;

    public void initEquipmentData(@NotNull Context context) {
        if(equipmentData == null) {
            equipmentData = new EquipmentItemStoreData();
            equipmentData.build(context);
        }
    }

    public void initPossessionData(@NotNull Context context) {
        if(possessionData == null) {
            possessionData = new PossessionItemStoreData();
            possessionData.build(context);
        }
    }

    public ItemStoreData getEquipmentData() {
        return equipmentData;
    }

    public ItemStoreData getPossessionData() {
        return possessionData;
    }

    public void setCurrentStore(ItemStoreData store) {
        currentStore = store;
    }

    public ItemStoreData getCurrentStore() {
        return currentStore;
    }
}