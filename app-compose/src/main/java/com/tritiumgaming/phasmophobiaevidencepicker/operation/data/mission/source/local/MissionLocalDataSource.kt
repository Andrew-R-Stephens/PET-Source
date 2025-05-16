package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.missions.Mission

class MissionLocalDataSource {

    fun fetchMissions(
        context: Context
    ): List<Mission>{

        val objectivesList = mutableListOf<Mission>()

        val resources = context.resources

        val missionsTypedArray = resources.obtainTypedArray(R.array.tasks_objectives_array)
        val missionsArray =
            ResourceUtils.intArrayFromTypedArray(resources, missionsTypedArray)

        for (i in missionsArray.indices) {
            objectivesList.add(Mission(missionsArray[i]))
        }

        return objectivesList

    }

}