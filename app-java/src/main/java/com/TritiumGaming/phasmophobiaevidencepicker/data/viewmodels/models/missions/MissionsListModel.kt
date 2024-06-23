package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions

import android.content.Context
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.R

/**
 * ObjectivesData class
 *
 * @author TritiumGamingStudios
 */
class MissionsListModel(context: Context) {

    val objectivesList = ArrayList<Objective>()

    init {
        val objectives = context.resources.getStringArray(R.array.tasks_objectives_array)
        for (i in objectives.indices) {
            objectivesList.add(Objective(objectives[i]))
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

    class Objective(val content: String, var missionId: Int? = null) {
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
            return "$selected $missionId $content"
        }

        override fun toString(): String {
            return content
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
