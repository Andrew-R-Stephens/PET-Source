package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.missions.MissionsListModel

class ObjectivesViewModel(application: Application): AndroidViewModel(application) {

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

    fun init() {
        missionsListModel = missionsListModel ?: MissionsListModel(getApplication())
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
