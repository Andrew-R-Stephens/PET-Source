package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.missions.MissionsListModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MissionRepository

class ObjectivesViewModel(
    missionRepository: MissionRepository
): ViewModel() {

    companion object {
        const val UNKNOWN: Response = 0
        const val ALONE: Response = 1
        const val GROUP: Response = 2

        const val NOT_COMPLETE: MissionStatus = false
        const val COMPLETE: MissionStatus = true
    }

    /* All possible objectives */
    var missionsListModel: MissionsListModel? = MissionsListModel(missionRepository.objectivesList)
    /* Objective Completed Buttons */
    var spinnerCompletionStatus: BooleanArray = BooleanArray(3) { NOT_COMPLETE }

    /* Ghost name */
    var ghostName: String? = null
        get() { return (field ?: "")}

    /* Response */
    var responseState: Response = UNKNOWN // alone , group

    fun toggleCompletionStatus(spinnerIndex: Int) {
        spinnerCompletionStatus[spinnerIndex] = !spinnerCompletionStatus[spinnerIndex]
    }

    fun reset() {
        ghostName = null
        missionsListModel?.reset()
        spinnerCompletionStatus.all { NOT_COMPLETE }
        responseState = UNKNOWN
    }


    class ObjectivesFactory(
        private val missionRepository: MissionRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ObjectivesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ObjectivesViewModel(
                    missionRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

typealias Response = Int
typealias MissionStatus = Boolean
