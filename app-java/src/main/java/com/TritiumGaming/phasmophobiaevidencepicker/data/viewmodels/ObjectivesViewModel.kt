package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions.MissionsListModel

class ObjectivesViewModel : ViewModel() {

    companion object {
        const val UNKNOWN: Response = 0
        const val ALONE: Response = 1
        const val GROUP: Response = 2

        const val NOT_COMPLETE: MissionStatus = false
        const val COMPLETE: MissionStatus = true
    }

    /* All possible objectives */
    var missionsListModel: MissionsListModel? = null
    /* Objective Completed Buttons */
    var spinnerCompletionStatus: BooleanArray = BooleanArray(3) { NOT_COMPLETE }

    /* Ghost name */
    var ghostName: String? = null
        get() { return (field ?: "")}

    /* Response */
    var responseState: Response = UNKNOWN // alone , group

    fun init(context: Context) {
        missionsListModel = missionsListModel ?: MissionsListModel(context)
    }

    fun toggleCompletionStatus(spinnerIndex: Int) {
        spinnerCompletionStatus[spinnerIndex] = !spinnerCompletionStatus[spinnerIndex]
    }

    fun reset() {
        ghostName = null
        missionsListModel?.reset()
        spinnerCompletionStatus.all { NOT_COMPLETE }
        responseState = UNKNOWN
    }

}

typealias Response = Int
typealias MissionStatus = Boolean
