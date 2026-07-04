package com.tritiumgaming.feature.missions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.missions.app.container.ObjectiveBoardContainerProvider
import com.tritiumgaming.feature.missions.ui.components.mission.MissionSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionUiState
import com.tritiumgaming.feature.missions.ui.components.name.NamesSpinnerUiState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.shared.data.operation.model.DifficultyData
import com.tritiumgaming.shared.data.operation.model.GhostDetails
import com.tritiumgaming.shared.data.operation.model.MissionData
import com.tritiumgaming.shared.data.operation.model.MissionState
import com.tritiumgaming.shared.data.operation.model.MissionStatus
import com.tritiumgaming.shared.data.operation.model.Response
import com.tritiumgaming.shared.data.operation.usecase.GetOperationStateUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationGhostDetailsUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationMissionDataUseCase
import com.tritiumgaming.shared.data.mission.model.Mission
import com.tritiumgaming.shared.data.mission.usecase.FetchAllMissionsUseCase
import com.tritiumgaming.shared.data.mission.usecase.MissionsUseCaseBundle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ObjectiveBoardViewModel(
    private val fetchAllMissionsUseCase: FetchAllMissionsUseCase,
    private val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase,
    private val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase,
    private val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase,
    private val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase,
    private val missionsUseCaseBundle: MissionsUseCaseBundle,
    getOperationStateUseCase: GetOperationStateUseCase = missionsUseCaseBundle.getOperationStateUseCase,
    private val updateOperationGhostDetailsUseCase: UpdateOperationGhostDetailsUseCase = missionsUseCaseBundle.updateOperationGhostDetailsUseCase,
    private val updateOperationMissionDataUseCase: UpdateOperationMissionDataUseCase = missionsUseCaseBundle.updateOperationMissionDataUseCase
): ViewModel() {

    private val _investigationState = getOperationStateUseCase()

    val difficultyState = _investigationState.map { it.difficulty }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DifficultyData()
        )

    val ghostDetailsState = _investigationState.map { it.ghostDetails }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = GhostDetails()
        )

    val missionDataState = _investigationState.map { it.missionData }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MissionData()
        )

    val missionSpinnerUiState: StateFlow<MissionSpinnerUiState> = missionDataState.map { missionData ->
        val filteredMissions = missionData.selectedMissions.fold(fetchAllMissions()) { acc, selected ->
            acc.filter { it.id != selected.mission.id }
        }
        MissionSpinnerUiState(
            selectedMissions = missionData.selectedMissions.map { MissionUiState(it.mission, it.status) },
            availableMissions = filteredMissions
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = MissionSpinnerUiState()
    )

    val namesSpinnerUiState: StateFlow<NamesSpinnerUiState> = ghostDetailsState.map { ghostDetails ->
        val filteredFirstNames = fetchAllFirstNames()
            .sortedBy { it.name }
            .filter { it.name != ghostDetails.firstName?.name }
        val filteredSurnames = fetchAllSurnames()
            .sortedBy { it.name }
            .filter { it.name != ghostDetails.surname?.name }
        NamesSpinnerUiState(
            firstNames = filteredFirstNames,
            surnames = filteredSurnames
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = NamesSpinnerUiState()
    )

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
        val currentMissionData = missionDataState.value
        val newMissions = currentMissionData.selectedMissions.map {
            if(it.mission.id == mission.id) it.copy(status = status) else it
        }
        updateOperationMissionDataUseCase(currentMissionData.copy(selectedMissions = newMissions))
    }

    fun selectMission(spinnerIndex: Int, mission: Mission) {
        val currentMissionData = missionDataState.value
        val newMissions = currentMissionData.selectedMissions.toMutableList()
        if(spinnerIndex < newMissions.size) {
            newMissions[spinnerIndex] = MissionState(mission, NOT_COMPLETE)
        }
        updateOperationMissionDataUseCase(currentMissionData.copy(selectedMissions = newMissions))
    }

    private fun initializeMissionUiState() {
        if(missionDataState.value.selectedMissions.isNotEmpty()) return

        val missions = fetchAllMissions()
        if(missions.isEmpty()) return

        val maxMissions = 3.coerceAtMost(missions.size)
        val newStates = List(maxMissions) { i ->
            MissionState(mission = missions[i], status = NOT_COMPLETE)
        }

        updateOperationMissionDataUseCase(MissionData(selectedMissions = newStates))
    }

    /*
     * Ghost details ---------------------
     */
    val ghostDetailsUiState: StateFlow<GhostDetailsUiState> = combine(
        ghostDetailsState,
        difficultyState
    ) { ghostDetails, difficultyState ->
        val ghostResponse = if(difficultyState.responseType ==
            DifficultyResources.DifficultyResponseType.UNKNOWN) { UNKNOWN }
            else { ghostDetails.responseState }

        GhostDetailsUiState(
            firstName = ghostDetails.firstName,
            surname = ghostDetails.surname,
            responseState = ghostResponse
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = GhostDetailsUiState()
    )

    fun setGhostFirstName(name: GhostName) {
        updateOperationGhostDetailsUseCase(
            ghostDetailsState.value.copy(firstName = name)
        )
    }

    fun setGhostSurname(name: GhostName) {
        updateOperationGhostDetailsUseCase(
            ghostDetailsState.value.copy(surname = name)
        )
    }

    fun setGhostResponse(response: Response) {
        updateOperationGhostDetailsUseCase(
            ghostDetailsState.value.copy(responseState = response)
        )
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

    init {
        initializeMissionUiState()
    }

    companion object {

        const val UNKNOWN: Int = 0
        const val ALONE: Int = 1
        const val GROUP: Int = 2

        const val NOT_COMPLETE: Boolean = false
        const val COMPLETE: Boolean = true

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as ObjectiveBoardContainerProvider).provideMissionsContainer()

                val fetchAllMissionsUseCase: FetchAllMissionsUseCase = container.fetchAllMissionsUseCase
                val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase = container.fetchAllFirstNamesUseCase
                val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase = container.fetchAllMaleNamesUseCase
                val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase = container.fetchAllFemaleNamesUseCase
                val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase = container.fetchAllSurnamesUseCase
                val missionsStateUseCaseBundle: MissionsUseCaseBundle = container.missionsUseCaseBundle

                ObjectiveBoardViewModel(
                    fetchAllMissionsUseCase = fetchAllMissionsUseCase,
                    fetchAllFirstNamesUseCase = fetchAllFirstNamesUseCase,
                    fetchAllMaleNamesUseCase = fetchAllMaleNamesUseCase,
                    fetchAllFemaleNamesUseCase = fetchAllFemaleNamesUseCase,
                    fetchAllSurnamesUseCase = fetchAllSurnamesUseCase,
                    missionsUseCaseBundle = missionsStateUseCaseBundle
                )
            }
        }
    }

}
