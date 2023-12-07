package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.equipment;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreItemData;

import java.util.ArrayList;

public class ItemStoreEquipmentGroupData extends ItemStoreGroupData {

    private @IntegerRes int buyCostData;

    public void setBuyCostData(@IntegerRes int buyCostData) {
        this.buyCostData = buyCostData;
    }

    public @IntegerRes int getBuyCostData() {
        return buyCostData;
    }

}
