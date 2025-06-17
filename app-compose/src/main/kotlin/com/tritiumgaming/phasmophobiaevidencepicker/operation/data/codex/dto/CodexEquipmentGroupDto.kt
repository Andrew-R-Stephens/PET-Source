package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentBuyCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentTitles
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment.CodexEquipmentGroup

data class CodexEquipmentGroupDto(
    val name: EquipmentTitles,
    val icon: EquipmentIcon,
    val buyCostData: EquipmentBuyCost,
    val items: List<CodexEquipmentGroupItemDto>
)

fun CodexEquipmentGroupDto.toDomain() = CodexEquipmentGroup(
    name = name,
    icon = icon,
    buyCostData = buyCostData,
    items = items.map { it.toDomain() }
)

fun List<CodexEquipmentGroupDto>.toDomain() = map { dto ->
    dto.toDomain()
}

