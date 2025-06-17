package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment.CodexEquipmentGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentTitles
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierInformation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierFlavorText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UpgradeCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UnlockLevel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierImage

data class CodexEquipmentGroupItemDto(
    val image: TierImage,
    val flavor: TierFlavorText,
    val info: TierInformation,
    val upgradeCostData: UpgradeCost,
    val upgradeLevelData: UnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
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