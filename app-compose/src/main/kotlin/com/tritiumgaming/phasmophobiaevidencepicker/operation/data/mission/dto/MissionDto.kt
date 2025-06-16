package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission

data class MissionDto(
    val mission: MissionContent
)

fun MissionDto.toDomain() = Mission(
    mission = mission
)

fun List<MissionDto>.toDomain() = map {
    it.toDomain()
}