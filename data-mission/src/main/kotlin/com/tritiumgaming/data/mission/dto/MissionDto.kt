package com.tritiumgaming.data.mission.dto

import com.tritiumgaming.shared.data.mission.mappers.MissionResources.MissionContent
import com.tritiumgaming.shared.data.mission.model.Mission

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