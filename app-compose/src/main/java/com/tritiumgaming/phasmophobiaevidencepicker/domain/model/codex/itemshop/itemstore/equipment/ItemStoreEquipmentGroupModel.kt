package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment

import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreGroupModel

class ItemStoreEquipmentGroupModel : ItemStoreGroupModel() {

    @IntegerRes
    var buyCostData: Int = 0
}
