package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.MissionDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.MissionDataSource

class MissionLocalDataSource(
): MissionDataSource {

    val missionResources
        get() = listOf(
            MissionResourceDto(mission = MissionContent.WITNESS_A_GHOST_EVENT),
            MissionResourceDto(mission = MissionContent.PHOTOGRAPH_THE_GHOST),
            MissionResourceDto(mission = MissionContent.GET_EMF_READING),
            MissionResourceDto(mission = MissionContent.GET_MOTION_SENSOR_ACTIVITY),
            MissionResourceDto(mission = MissionContent.SMUDGE_THE_GHOST_LOCATION),
            MissionResourceDto(mission = MissionContent.PREVENT_HUNT_WITH_CRUCIFIX),
            MissionResourceDto(mission = MissionContent.GHOST_WALKS_THROUGH_SALT),
            MissionResourceDto(mission = MissionContent.ESCAPE_A_GHOST_HUNT),
            MissionResourceDto(mission = MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE),
            MissionResourceDto(mission = MissionContent.GHOST_BLOW_OUT_CANDLE),
            MissionResourceDto(mission = MissionContent.GET_AVERAGE_SANITY_AT_OR_BELOW_25)
        )

    override fun get(): Result<List<MissionDto>> {

        return Result.success(missionResources.map { it.toMissionDto() })

    }

}

fun MissionResourceDto.toMissionDto() = MissionDto(
    mission = mission
)

data class MissionResourceDto(
    val mission: MissionContent
)