package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.mappers.GhostNameResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.model.GhostName

data class GhostNameDto(
    val name: GhostNameResources.Name,
    val priority: GhostName.NamePriority,
    val gender: GhostName.Gender
)

fun GhostNameDto.toDomain() = GhostName(
    name = name,
    priority = priority,
    gender = gender
)

fun List<GhostNameDto>.toDomain() = map {
    it.toDomain()
}