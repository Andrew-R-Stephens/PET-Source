package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.MissionDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.MissionDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent

class MissionLocalDataSource(
    private val applicationContext: Context
): MissionDataSource {

    val missionResources
        get() = listOf(
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_ghostevent),
                content = MissionContent.WITNESS_A_GHOST_EVENT),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_ghostphotograph),
                content = MissionContent.PHOTOGRAPH_THE_GHOST),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_emfreader),
                content = MissionContent.GET_EMF_READING),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_motionsensor),
                content = MissionContent.GET_MOTION_SENSOR_ACTIVITY),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_smudgestick),
                content = MissionContent.SMUDGE_THE_GHOST_LOCATION),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_crucifix),
                content = MissionContent.PREVENT_HUNT_WITH_CRUCIFIX),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_salt),
                content = MissionContent.GHOST_WALKS_THROUGH_SALT),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_escapehunt),
                content = MissionContent.ESCAPE_A_GHOST_HUNT),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_repelwithsmudge),
                content = MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_extinguishcandle),
                content = MissionContent.GHOST_BLOW_OUT_CANDLE),
            MissionResourceDto(
                id = applicationContext.getString(R.string.objective_id_lowsanity),
                content = MissionContent.GET_AVERAGE_SANITY_AT_OR_BELOW_25)
        )

    override fun get(): Result<List<MissionDto>> {
        return Result.success(missionResources.map { it.toMissionDto() })
    }
}

fun MissionResourceDto.toMissionDto() = MissionDto(
    id = content.name,
    content = content
)

data class MissionResourceDto(
    val id: String,
    val content: MissionContent
)