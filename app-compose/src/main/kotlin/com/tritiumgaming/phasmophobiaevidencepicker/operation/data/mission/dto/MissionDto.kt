package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission

data class MissionDto(
    val id: String,
    val content: MissionContent,
)

fun MissionDto.toDomain() = Mission(
    id = id,
    content = content,
)

fun List<MissionDto>.toDomain() = map {
    it.toDomain()
}