package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.TierFlavorText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.TierImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.TierInformation
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.UnlockLevel
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.UpgradeCost
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.CodexEquipmentGroupItem

data class CodexEquipmentGroupItemDto(
    val image: TierImage,
    val flavor: TierFlavorText,
    val info: TierInformation,
    val upgradeCost: UpgradeCost,
    val upgradeLevel: UnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
)

fun CodexEquipmentGroupItemDto.toDomain() =
    CodexEquipmentGroupItem(
        image = image,
        flavor = flavor,
        info = info,
        upgradeCostData = upgradeCost,
        upgradeLevelData = upgradeLevel,
        positiveAttributes = positiveAttributes,
        negativeAttributes = negativeAttributes
    )