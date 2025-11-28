package com.tritiumgaming.shared.data.codex.model.equipment

data class EquipmentTypeTier(
    val image: com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierImage,
    val flavor: com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierFlavorText,
    val info: com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierInformation,
    val upgradeCostData: com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUpgradeCost,
    val upgradeLevelData: com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUnlockLevel,
    val positiveAttributes: List<com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentAttribute>,
    val negativeAttributes: List<com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentAttribute>
)