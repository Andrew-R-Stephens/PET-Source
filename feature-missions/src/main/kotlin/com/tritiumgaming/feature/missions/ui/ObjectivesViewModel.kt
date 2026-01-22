package com.tritiumgaming.feature.missions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.missions.app.container.MissionsContainerProvider
import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.shared.data.mission.model.Mission
import com.tritiumgaming.shared.data.mission.usecase.FetchAllMissionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ObjectivesViewModel(
    private val fetchAllMissionsUseCase: FetchAllMissionsUseCase,
    private val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase,
    private val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase,
    private val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase,
    private val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase
): ViewModel() {

    private val _missionSpinnerUiState: MutableStateFlow<MissionSpinnerUiState> =
        MutableStateFlow(MissionSpinnerUiState(
            availableMissions = fetchAllMissions()
        ))
    val missionSpinnerUiState = _missionSpinnerUiState.asStateFlow()

    private val _namesSpinnerUiState: MutableStateFlow<NamesSpinnerUiState> =
        MutableStateFlow(NamesSpinnerUiState(
            firstNames = fetchAllFirstNames(),
            surnames = fetchAllSurnames()
        ))
    val namesSpinnerUiState = _namesSpinnerUiState.asStateFlow()

    /*private val _missionUiState: MutableStateFlow<List<MissionUiState>> =
        MutableStateFlow(emptyList())
    val missionUiState = _missionUiState.asStateFlow()*/

    /*
     * Missions -------------------------
     */
    fun fetchAllMissions(): List<Mission> {
        return try {
            fetchAllMissionsUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun updateMissionStatus(mission: Mission, status: MissionStatus) {
        /*_missionUiState.update {
            it.map { spinnerState ->
                if(spinnerState.mission.id == mission.id) {
                    spinnerState.copy(status = status)
                } else {
                    spinnerState
                }
            }
        }*/

        _missionSpinnerUiState.update {
            it.copy(
                selectedMissions = it.selectedMissions.map { spinnerState ->
                    if(spinnerState.mission.id == mission.id) {
                        spinnerState.copy(status = status)
                    } else {
                        spinnerState
                    }
                }
            )

        }
    }

    fun selectMission(spinnerIndex: Int, mission: Mission) {

        /*_missionUiState.update {
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
        }*/

        _missionSpinnerUiState.update {
            it.copy(
                selectedMissions = it.selectedMissions.mapIndexed { index, spinnerState ->
                    if(spinnerIndex == index) {
                        spinnerState.copy(
                            mission = mission,
                            status = NOT_COMPLETE
                        )
                    } else {
                        spinnerState
                    }
                }
            )
        }

        updateMissionSpinnerUiState()

    }

    private fun initializeMissionUiState() {
        val missions = try {
            fetchAllMissionsUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        val maxMissions = 3.coerceAtMost(missions.size)

        val newStates: MutableList<MissionUiState> = mutableListOf()
        for(i in 0 until maxMissions) {
            newStates.add(
                MissionUiState(
                    mission = missions[i],
                    status = NOT_COMPLETE
                )
            )
        }
        /*
        _missionUiState.update {
            newStates
        }
        */

        _missionSpinnerUiState.update {
            it.copy(
                selectedMissions = newStates
            )
        }


    }

    /*
     * Mission Spinners -------------------------
     */

    private fun updateMissionSpinnerUiState() {
        /*
        val filteredMissions = missionUiState.value
            .fold(fetchAllMissions()) { missionsUi, mission ->
                missionsUi.filter { it.id != mission.mission.id }
            }
        */
        val filteredMissions = missionSpinnerUiState.value.selectedMissions
            .fold(fetchAllMissions()) { missionsUi, mission ->
                missionsUi.filter { it.id != mission.mission.id }
            }

        _missionSpinnerUiState.update {
            it.copy(
                availableMissions = filteredMissions
            )
        }
    }

    /*
     * Ghost details ---------------------
     */
    private val _ghostDetailsUiState = MutableStateFlow(GhostDetailsUiState())
    val ghostDetailsUiState = _ghostDetailsUiState.asStateFlow()
    fun setGhostFirstName(name: GhostName) {
        _ghostDetailsUiState.update {
            it.copy(
                firstName = name
            )
        }

        updateFirstNamesOfNameSpinnerUiState(name)
    }

    fun setGhostSurname(name: GhostName) {
        _ghostDetailsUiState.update {
            it.copy(
                surname = name
            )
        }

        updateSurnamesOfNamesSpinnerUiState(name)
    }

    fun setGhostResponse(response: Response) {
        _ghostDetailsUiState.update {
            it.copy(
                responseState = response
            )
        }
    }

    /*
     * Name Spinners ------------------
     */

    private fun updateFirstNamesOfNameSpinnerUiState(name: GhostName) {
        val filteredFirstNames = fetchAllFirstNames()
            .sortedBy { ghostName -> ghostName.name }
            .filter { it.name != name.name }

        _namesSpinnerUiState.update {
            it.copy(
                firstNames = filteredFirstNames
            )
        }
    }

    private fun updateSurnamesOfNamesSpinnerUiState(name: GhostName) {
        val filteredSurnames = fetchAllSurnames()
            .sortedBy { ghostName -> ghostName.name }
            .filter { it.name != name.name }

        _namesSpinnerUiState.update {
            it.copy(
                surnames = filteredSurnames
            )
        }
    }

    private fun fetchAllFirstNames(): List<GhostName> {
        return try {
            fetchAllFirstNamesUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun fetchAllSurnames(): List<GhostName> {
        return try {
            fetchAllSurnamesUseCase().getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /*fun reset() {
        // ghostName = null
        _ghostDetailsUiState.update {
            it.copy(
                firstName = null,
                surname = null,
                responseState = UNKNOWN
            )
        }
    }*/

    init {
        initializeMissionUiState()
        updateMissionSpinnerUiState()
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
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MissionsContainerProvider).provideMissionsContainer()

                val fetchAllMissionsUseCase: FetchAllMissionsUseCase = container.fetchAllMissionsUseCase
                val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase = container.fetchAllFirstNamesUseCase
                val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase = container.fetchAllMaleNamesUseCase
                val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase = container.fetchAllFemaleNamesUseCase
                val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase = container.fetchAllSurnamesUseCase

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