package com.tritiumgaming.shared.operation.domain.codex.model.equipment

import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentBuyCost
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentTitle

data class EquipmentType(
    val id: EquipmentIdentifier,
    val name: EquipmentTitle,
    val icon: EquipmentIcon,
    val buyCostData: EquipmentBuyCost,
    val items: List<EquipmentTypeTier>
) {
    val size: Int = items.size
}
