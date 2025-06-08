package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ResourceUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source.MissionDataSource

class MissionLocalDataSource(
    private val applicationContext: Context
): MissionDataSource {

    override fun fetchMissions(): Result<List<Mission>> {

        val resources = applicationContext.resources

        val objectivesList = mutableListOf<Mission>()

        val missionsTypedArray = resources.obtainTypedArray(R.array.tasks_objectives_array)
        val missionsArray =
            ResourceUtils.intArrayFromTypedArray(resources, missionsTypedArray)

        for (i in missionsArray.indices) {
            objectivesList.add(Mission(missionsArray[i]))
        }

        return Result.success(objectivesList)

    }

}