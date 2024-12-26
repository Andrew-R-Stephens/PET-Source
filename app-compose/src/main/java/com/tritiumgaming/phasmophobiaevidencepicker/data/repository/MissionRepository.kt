package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.missions.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class MissionRepository(
    context: Context
) {

    val objectivesList = ArrayList<Mission>()

    init {
        val missionsTypedArray = context.resources.obtainTypedArray(R.array.tasks_objectives_array)
        val missionsArray = intArrayFromTypedArray(context.resources, missionsTypedArray)

        for (i in missionsArray.indices) {
            objectivesList.add(Mission(missionsArray[i]))
        }
    }

}
