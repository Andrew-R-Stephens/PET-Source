package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment

import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreGroup

class ItemStoreEquipmentGroupModel : ItemStoreGroup() {

    @IntegerRes
    var buyCostData: Int = 0
}
