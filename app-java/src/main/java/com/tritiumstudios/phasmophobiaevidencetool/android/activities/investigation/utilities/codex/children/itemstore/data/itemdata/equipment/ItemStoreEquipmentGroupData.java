package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment;

import androidx.annotation.IntegerRes;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;

public class ItemStoreEquipmentGroupData extends ItemStoreGroupData {

    private @IntegerRes int buyCostData;

    public void setBuyCostData(@IntegerRes int buyCostData) {
        this.buyCostData = buyCostData;
    }

    public @IntegerRes int getBuyCostData() {
        return buyCostData;
    }

}
