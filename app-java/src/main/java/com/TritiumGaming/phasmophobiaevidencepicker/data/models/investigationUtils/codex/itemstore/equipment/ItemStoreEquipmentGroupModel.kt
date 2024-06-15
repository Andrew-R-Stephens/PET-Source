package com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore.equipment

import androidx.annotation.IntegerRes
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore.ItemStoreGroupModel

class ItemStoreEquipmentGroupModel : ItemStoreGroupModel() {
    @JvmField
    @get:IntegerRes
    @IntegerRes
    var buyCostData: Int = 0
}
