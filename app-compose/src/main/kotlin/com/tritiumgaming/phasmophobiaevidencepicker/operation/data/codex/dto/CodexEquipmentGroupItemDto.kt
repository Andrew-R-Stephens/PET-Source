package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment.CodexEquipmentGroupItem

data class CodexEquipmentGroupItemDto(
    @DrawableRes val image: Int,
    @StringRes val flavor: Int,
    @StringRes val info: Int,
    @IntegerRes var upgradeCostData: Int,
    @IntegerRes var upgradeLevelData: Int,
    @StringRes val positiveAttributes: List<Int> = ArrayList(),
    @StringRes val negativeAttributes: List<Int> = ArrayList()
)

fun CodexEquipmentGroupItemDto.toDomain() =
    CodexEquipmentGroupItem(
        image = image,
        flavor = flavor,
        info = info,
        upgradeCostData = upgradeCostData,
        upgradeLevelData = upgradeLevelData,
        positiveAttributes = positiveAttributes,
        negativeAttributes = negativeAttributes
    )