package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.MissionsListModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository

class ObjectivesViewModel(
    missionRepository: MissionRepository
): ViewModel() {

    /* All possible objectives */
    var missionsListModel: MissionsListModel? =
        MissionsListModel(missionRepository.getMissions().getOrDefault(emptyList()))

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

    companion object {

        const val UNKNOWN: Response = 0
        const val ALONE: Response = 1
        const val GROUP: Response = 2

        const val NOT_COMPLETE: MissionStatus = false
        const val COMPLETE: MissionStatus = true


        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val missionRepository: MissionRepository = appKeyContainer.missionRepository

                ObjectivesViewModel(
                    missionRepository = missionRepository
                )
            }
        }
    }
}

typealias Response = Int
typealias MissionStatus = Boolean
