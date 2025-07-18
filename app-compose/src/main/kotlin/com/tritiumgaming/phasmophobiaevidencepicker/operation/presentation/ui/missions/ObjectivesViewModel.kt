package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.usecase.FetchAllMissionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

typealias Response = Int
typealias MissionStatus = Boolean

class ObjectivesViewModel(
    private val fetchAllMissionsUseCase: FetchAllMissionsUseCase,
    private val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase,
    private val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase,
    private val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase,
    private val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase,
    ): ViewModel() {

    data class MissionSpinnerUiState(
        val mission: Mission,
        val status: MissionStatus
    )

    data class GhostDetailsUiState(
        val firstName: GhostName? = null,
        val surname: GhostName? = null,
        val responseState: Response = UNKNOWN
    )

    private val _missionSpinnersUiState: MutableStateFlow<List<MissionSpinnerUiState>> =
        MutableStateFlow(emptyList())
    val missionSpinnersUiState = _missionSpinnersUiState.asStateFlow()

    /*
     * Mission Spinners -------------------------
     */
    fun fetchAllMissions(): List<Mission> {
        return try {
            fetchAllMissionsUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /*
     * Mission Spinners -------------------------
     */
    private fun initializeMissionSpinners() {
        val missions = try {
            fetchAllMissionsUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        val maxMissions = 3.coerceAtMost(missions.size)

        val newStates: MutableList<MissionSpinnerUiState> = mutableListOf()
        for(i in 0 until maxMissions) {
            newStates.add(
                MissionSpinnerUiState(
                    mission = missions[i],
                    status = NOT_COMPLETE
                )
            )
        }

        _missionSpinnersUiState.update {
            newStates
        }
    }
    fun updateMissionStatus(mission: Mission, status: MissionStatus) {
        _missionSpinnersUiState.update {
            it.map { spinnerState ->
                if(spinnerState.mission.id == mission.id) {
                    spinnerState.copy(status = status)
                } else {
                    spinnerState
                }
            }
        }
    }
    fun selectMission(spinnerIndex: Int, mission: Mission) {
        _missionSpinnersUiState.update {
            it.mapIndexed { index, spinnerState ->
                if(spinnerIndex == index) {
                    spinnerState.copy(
                        mission = mission,
                        status = NOT_COMPLETE
                    )
                } else {
                    spinnerState
                }
            }
        }
    }

    /* Ghost details */
    private val _ghostDetailsUiState = MutableStateFlow(GhostDetailsUiState())
    val ghostDetailsUiState = _ghostDetailsUiState.asStateFlow()
    fun setGhostFirstName(name: GhostName) {
        _ghostDetailsUiState.update {
            it.copy(
                firstName = name
            )
        }
    }
    fun setGhostLastName(name: GhostName) {
        _ghostDetailsUiState.update {
            it.copy(
                surname = name
            )
        }
    }
    fun setGhostResponse(response: Response) {
        _ghostDetailsUiState.update {
            it.copy(
                responseState = response
            )
        }
    }

    fun reset() {
        // ghostName = null
        _ghostDetailsUiState.update {
            it.copy(
                firstName = null,
                surname = null,
                responseState = UNKNOWN
            )
        }
    }

    fun fetchAllFirstNames(): List<GhostName> {
        return try {
            fetchAllFirstNamesUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun fetchAllSurnamesNames(): List<GhostName> {
        return try {
            fetchAllSurnamesUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    init {
        initializeMissionSpinners()
    }

    class ObjectivesFactory(
        private val fetchAllMissionsUseCase: FetchAllMissionsUseCase,
        private val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase,
        private val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase,
        private val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase,
        private val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ObjectivesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ObjectivesViewModel(
                    fetchAllMissionsUseCase = fetchAllMissionsUseCase,
                    fetchAllFirstNamesUseCase = fetchAllFirstNamesUseCase,
                    fetchAllMaleNamesUseCase = fetchAllMaleNamesUseCase,
                    fetchAllFemaleNamesUseCase = fetchAllFemaleNamesUseCase,
                    fetchAllSurnamesUseCase = fetchAllSurnamesUseCase
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
                val appKeyContainer =
                    (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val fetchAllMissionsUseCase: FetchAllMissionsUseCase = appKeyContainer.fetchAllMissionsUseCase
                val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase = appKeyContainer.fetchAllFirstNamesUseCase
                val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase = appKeyContainer.fetchAllMaleNamesUseCase
                val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase = appKeyContainer.fetchAllFemaleNamesUseCase
                val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase = appKeyContainer.fetchAllSurnamesUseCase

                ObjectivesViewModel(
                    fetchAllMissionsUseCase = fetchAllMissionsUseCase,
                    fetchAllFirstNamesUseCase = fetchAllFirstNamesUseCase,
                    fetchAllMaleNamesUseCase = fetchAllMaleNamesUseCase,
                    fetchAllFemaleNamesUseCase = fetchAllFemaleNamesUseCase,
                    fetchAllSurnamesUseCase = fetchAllSurnamesUseCase
                )
            }
        }
    }

}