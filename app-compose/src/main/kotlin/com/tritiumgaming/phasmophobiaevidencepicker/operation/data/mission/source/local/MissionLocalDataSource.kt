package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.MissionDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source.MissionDataSource

class MissionLocalDataSource(
): MissionDataSource {

    val missionResources
        get() = listOf(
            MissionResource(data = R.string.objective_info_ghostevent),
            MissionResource(data = R.string.objective_info_ghostphotograph),
            MissionResource(data = R.string.objective_info_emfreader),
            MissionResource(data = R.string.objective_info_motionsensor),
            MissionResource(data = R.string.objective_info_smudgestick),
            MissionResource(data = R.string.objective_info_crucifix),
            MissionResource(data = R.string.objective_info_salt),
            MissionResource(data = R.string.objective_info_escapehunt),
            MissionResource(data = R.string.objective_info_repelwithsmudge),
            MissionResource(data = R.string.objective_info_extinguishcandle),
            MissionResource(data = R.string.objective_info_lowsanity)
        )

    override fun get(): Result<List<MissionDto>> {

        val objectivesList = mutableListOf<MissionDto>()

        missionResources.forEach { resDto ->
            objectivesList.add(
                MissionDto(resDto.data)
            )
        }

        return Result.success(objectivesList)

    }

}

data class MissionResource(
    @StringRes val data: Int
)