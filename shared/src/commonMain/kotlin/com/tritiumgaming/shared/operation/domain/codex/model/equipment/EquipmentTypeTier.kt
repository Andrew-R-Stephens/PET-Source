package com.tritiumgaming.shared.operation.domain.codex.model.equipment

import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentTierFlavorText
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentTierImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentTierInformation
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentUnlockLevel
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentUpgradeCost

data class EquipmentTypeTier(
    val image: EquipmentTierImage,
    val flavor: EquipmentTierFlavorText,
    val info: EquipmentTierInformation,
    val upgradeCostData: EquipmentUpgradeCost,
    val upgradeLevelData: EquipmentUnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
)