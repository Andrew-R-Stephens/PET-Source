package com.tritiumgaming.shared.data.codex.model.equipment

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.*

data class EquipmentTypeTier(
    val image: EquipmentTierImage,
    val flavor: EquipmentTierFlavorText,
    val info: EquipmentTierInformation,
    val upgradeCostData: EquipmentUpgradeCost,
    val upgradeLevelData: EquipmentUnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
)