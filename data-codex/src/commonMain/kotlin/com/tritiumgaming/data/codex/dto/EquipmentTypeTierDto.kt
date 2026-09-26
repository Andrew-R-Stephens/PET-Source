package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentAttribute
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierFlavorText
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierImage
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierInformation
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUnlockLevel
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUpgradeCost
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier

data class EquipmentTypeTierDto(
    val image: EquipmentTierImage,
    val flavor: EquipmentTierFlavorText,
    val info: EquipmentTierInformation,
    val upgradeCost: EquipmentUpgradeCost,
    val upgradeLevel: EquipmentUnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
)

fun EquipmentTypeTierDto.toDomain() =
    EquipmentTypeTier(
        image = image,
        flavor = flavor,
        info = info,
        upgradeCostData = upgradeCost,
        upgradeLevelData = upgradeLevel,
        positiveAttributes = positiveAttributes,
        negativeAttributes = negativeAttributes
    )