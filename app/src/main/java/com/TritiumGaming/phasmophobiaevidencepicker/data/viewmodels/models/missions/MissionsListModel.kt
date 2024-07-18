package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ResourceUtils.ResourceUtils.intArrayFromTypedArray

/**
 * ObjectivesData class
 *
 * @author TritiumGamingStudios
 */
class MissionsListModel(context: Context) {

    private val objectivesList = ArrayList<Objective>()

    init {
        val missionsTypedArray = context.resources.obtainTypedArray(R.array.tasks_objectives_array)
        val missionsArray = intArrayFromTypedArray(context.resources, missionsTypedArray)

        for (i in missionsArray.indices) {
            objectivesList.add(Objective(missionsArray[i]))
        }
    }

    fun getMissionsBy(state: Boolean, missionId: Int? = null): List<Objective> {
        return objectivesList.filter { objective ->
            objective.selected == state || (objective.missionId == missionId) }
    }

    /** @return The first Objective matching the missionId (the is of the spinner used). If nothing
     * is found, it returns the first unselected Objective as a default. */
    fun selectMatchOrFirstAvailable(missionId: Int): Objective? {
        var target: Objective? = null
        objectivesList.forEach { objective ->
            if (objective.missionId == missionId) {
                return objective
            } else {
                if (target == null && !objective.selected) { target = objective }
            }
        }
        target?.select(missionId)
        return target
    }

    fun deselectBy(missionId: Int? = null, state: Boolean? = null) {
        objectivesList.forEach { objective ->
            if(objective.missionId == missionId) {
                objective.deselect()
                return
            }
        }
    }

    fun deselectAllByMissionId(missionId: Int) {
        objectivesList.forEach { objective ->
            if(objective.missionId == missionId)
                objective.deselect()
        }
    }

    fun reset() {
        objectivesList.forEach { objective -> objective.deselect() }
    }

    class Objective(val contentRes: Int, var missionId: Int? = null) {
        var selected: Boolean = false

        fun select(missionId: Int) {
            selected = true
            this.missionId = missionId
        }

        fun deselect() {
            selected = false
            missionId = null
        }

        fun getData(): String {
            return "$selected $missionId $contentRes"
        }

        fun getContent(context: Context): String {
            return context.getString(contentRes)
        }
    }

    override fun toString(): String {
        val str: StringBuilder = StringBuilder()
        objectivesList.forEach { objective ->
            str.append("${objective.getData()}\n")
        }
        return str.toString()
    }

}
