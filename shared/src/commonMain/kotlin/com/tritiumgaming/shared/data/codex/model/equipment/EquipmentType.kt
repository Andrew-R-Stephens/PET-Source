package com.tritiumgaming.shared.data.codex.model.equipment

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources

data class EquipmentType(
    val id: EquipmentResources.EquipmentIdentifier,
    val name: EquipmentResources.EquipmentTitle,
    val icon: EquipmentResources.EquipmentIcon,
    val buyCostData: EquipmentResources.EquipmentBuyCost,
    val items: List<EquipmentTypeTier>
) {
    val size: Int = items.size
}