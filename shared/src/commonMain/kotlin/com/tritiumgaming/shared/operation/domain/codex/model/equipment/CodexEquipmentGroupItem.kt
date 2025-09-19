package com.tritiumgaming.shared.operation.domain.codex.model.equipment

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.TierFlavorText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.TierImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.TierInformation
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.UnlockLevel
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.UpgradeCost

data class CodexEquipmentGroupItem(
    val image: TierImage,
    val flavor: TierFlavorText,
    val info: TierInformation,
    val upgradeCostData: UpgradeCost,
    val upgradeLevelData: UnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
)