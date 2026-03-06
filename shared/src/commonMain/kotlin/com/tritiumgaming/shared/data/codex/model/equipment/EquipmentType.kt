package com.tritiumgaming.shared.data.codex.model.equipment

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.*

data class EquipmentType(
    val id: EquipmentIdentifier,
    val name: EquipmentTitle,
    val icon: EquipmentIcon,
    val buyCostData: EquipmentBuyCost,
    val items: List<EquipmentTypeTier>
) {
    val size: Int = items.size
}