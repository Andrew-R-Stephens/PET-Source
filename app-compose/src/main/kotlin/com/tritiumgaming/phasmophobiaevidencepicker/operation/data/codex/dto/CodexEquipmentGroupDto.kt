package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentBuyCost
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentTitles
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.CodexEquipmentGroup

data class CodexEquipmentGroupDto(
    val name: EquipmentTitles,
    val icon: EquipmentIcon,
    val buyCost: EquipmentBuyCost,
    val items: List<CodexEquipmentGroupItemDto>
)

fun CodexEquipmentGroupDto.toDomain() = CodexEquipmentGroup(
    name = name,
    icon = icon,
    buyCostData = buyCost,
    items = items.map { it.toDomain() }
)

fun List<CodexEquipmentGroupDto>.toDomain() = map { dto ->
    dto.toDomain()
}

