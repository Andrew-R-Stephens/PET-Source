package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentBuyCost
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIcon
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType

data class EquipmentTypeDto(
    val id: EquipmentIdentifier,
    val name: EquipmentTitle,
    val icon: EquipmentIcon,
    val buyCost: EquipmentBuyCost,
    val items: List<EquipmentTypeTierDto>
)

fun EquipmentTypeDto.toDomain() = EquipmentType(
    id = id,
    name = name,
    icon = icon,
    buyCostData = buyCost,
    items = items.map { it.toDomain() }
)

fun List<EquipmentTypeDto>.toDomain() = map { dto ->
    dto.toDomain()
}

