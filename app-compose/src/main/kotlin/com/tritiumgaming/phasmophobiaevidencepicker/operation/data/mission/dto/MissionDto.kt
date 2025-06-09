package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission

data class MissionDto(
    @StringRes val content: Int
)

fun MissionDto.toDomain() = Mission(
    contentRes = content
)

fun List<MissionDto>.toDomain() = map {
    it.toDomain()
}