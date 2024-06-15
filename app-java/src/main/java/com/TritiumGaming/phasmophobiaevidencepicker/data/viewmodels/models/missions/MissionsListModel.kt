package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R

/**
 * ObjectivesData class
 *
 * @author TritiumGamingStudios
 */
class MissionsListModel(context: Context) {
    private val objectivePool = ArrayList<Objective>()

    init {
        val objectives = context.resources.getStringArray(R.array.tasks_objectives_array)
        for (i in objectives.indices) {
            objectivePool.add(Objective(objectives[i], i))
        }
    }

    fun getCopyOfObjective(o: Objective): Objective? {
        for (objective in objectivePool) {
            if (o.content == objective.content) {
                return objective
            }
        }
        return null
    }

    fun getObjectivesOfSelectedState(selectedState: Boolean): ArrayList<Objective> {
        val temp = ArrayList<Objective>()
        for (o in objectivePool) {
            if (o.selected == selectedState) {
                temp.add(o)
            }
        }
        return temp
    }

    /**
     * Objective class
     */
    class Objective(val content: String, var position: Int) {
        var selected: Boolean = false

        override fun toString(): String {
            return content
        }
    }
}
