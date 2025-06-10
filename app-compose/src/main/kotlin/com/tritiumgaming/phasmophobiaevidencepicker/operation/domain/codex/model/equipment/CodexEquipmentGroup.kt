package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

data class CodexEquipmentGroup(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @IntegerRes var buyCostData: Int,
    val items: List<CodexEquipmentGroupItem>
) {
    val size: Int = items.size
}
