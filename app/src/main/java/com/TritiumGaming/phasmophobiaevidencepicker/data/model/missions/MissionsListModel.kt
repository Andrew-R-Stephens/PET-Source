package com.TritiumGaming.phasmophobiaevidencepicker.data.model.missions

/**
 * ObjectivesData class
 *
 * @author TritiumGamingStudios
 */
class MissionsListModel(
    private var objectivesList: ArrayList<Mission> = arrayListOf()
) {

    fun getMissionsBy(state: Boolean, missionId: Int? = null): List<Mission> {
        return objectivesList.filter { objective ->
            objective.selected == state || (objective.missionId == missionId) }
    }

    /** @return The first Objective matching the missionId (the is of the spinner used). If nothing
     * is found, it returns the first unselected Objective as a default. */
    fun selectMatchOrFirstAvailable(missionId: Int): Mission? {
        var target: Mission? = null
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

    override fun toString(): String {
        val str: StringBuilder = StringBuilder()
        objectivesList.forEach { objective ->
            str.append("${objective.getData()}\n")
        }
        return str.toString()
    }

}
