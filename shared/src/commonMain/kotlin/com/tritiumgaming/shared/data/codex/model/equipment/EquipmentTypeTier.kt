package com.tritiumgaming.shared.data.codex.model.equipment

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources

data class EquipmentTypeTier(
    val image: EquipmentResources.EquipmentTierImage,
    val flavor: EquipmentResources.EquipmentTierFlavorText,
    val info: EquipmentResources.EquipmentTierInformation,
    val upgradeCostData: EquipmentResources.EquipmentUpgradeCost,
    val upgradeLevelData: EquipmentResources.EquipmentUnlockLevel,
    val positiveAttributes: List<EquipmentResources.EquipmentAttribute>,
    val negativeAttributes: List<EquipmentResources.EquipmentAttribute>
)