package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.missions.MissionsListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ObjectivesViewModel(
    missionRepository: MissionRepository
): ViewModel() {

    /* All possible objectives */
    var missionsListModel: MissionsListModel? = MissionsListModel(missionRepository.objectivesList)
    /* Objective Completed Buttons */
    var spinnerCompletionStatus: BooleanArray = BooleanArray(3) { NOT_COMPLETE }

    /* Ghost name */
    private val _ghostName = MutableStateFlow("")
    val ghostName = _ghostName.asStateFlow()
    fun setGhostName(ghostName: String? = null) {
        _ghostName.update { ghostName ?: "" }
    }

    /* Response */
    private var _responseState = MutableStateFlow<Response>(UNKNOWN) // alone , group
    val responseState = _responseState.asStateFlow()
    fun setResponseState(response: Response = UNKNOWN) {
        _responseState.update { response }
    }

    fun toggleCompletionStatus(spinnerIndex: Int) {
        spinnerCompletionStatus[spinnerIndex] = !spinnerCompletionStatus[spinnerIndex]
    }

    fun reset() {
        setGhostName()
        missionsListModel?.reset()
        spinnerCompletionStatus.all { NOT_COMPLETE }
        _responseState.update { UNKNOWN }
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
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).container

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
