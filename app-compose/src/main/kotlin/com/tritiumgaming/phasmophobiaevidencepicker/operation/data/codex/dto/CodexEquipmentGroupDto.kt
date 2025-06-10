package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment.CodexEquipmentGroup

data class CodexEquipmentGroupDto(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @IntegerRes var buyCostData: Int,
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

