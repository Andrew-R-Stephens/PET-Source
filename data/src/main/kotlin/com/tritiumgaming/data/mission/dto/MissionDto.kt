package com.tritiumgaming.data.mission.dto

import com.tritiumgaming.shared.operation.domain.mission.mappers.MissionResources.MissionContent
import com.tritiumgaming.shared.operation.domain.mission.model.Mission

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