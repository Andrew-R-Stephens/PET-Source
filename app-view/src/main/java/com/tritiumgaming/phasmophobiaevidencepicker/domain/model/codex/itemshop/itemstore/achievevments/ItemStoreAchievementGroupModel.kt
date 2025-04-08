package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.achievevments

import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreGroup

class ItemStoreAchievementGroupModel : ItemStoreGroup() {

    @IntegerRes
    var buyCostData: Int = 0
}
