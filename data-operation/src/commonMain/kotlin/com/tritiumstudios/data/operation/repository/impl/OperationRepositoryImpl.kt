package com.tritiumstudios.data.operation.repository.impl

import com.tritiumgaming.data.ghost.mapper.GhostResources
import com.tritiumgaming.data.journal.model.EvidenceState
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources
import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.DifficultyData
import com.tritiumstudios.data.operation.model.GhostDetails
import com.tritiumstudios.data.operation.model.MapData
import com.tritiumstudios.data.operation.model.MissionData
import com.tritiumstudios.data.operation.model.OperationData
import com.tritiumstudios.data.operation.model.OperationOverrideData
import com.tritiumstudios.data.operation.model.PhaseData
import com.tritiumstudios.data.operation.model.SanityData
import com.tritiumstudios.data.operation.model.TemperatureData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OperationRepositoryImpl : OperationRepository {

    private val _state = MutableStateFlow(OperationData())
    override val state = _state.asStateFlow()

    override fun updateMap(map: MapData) {
        _state.update { it.copy(map = map) }
    }

    override fun updateSanity(insanity: Float, sanity: Float) {
        _state.update { it.copy(sanity = SanityData(insanity, sanity)) }
    }

    override fun updateSanity(sanity: SanityData) {
        _state.update { it.copy(sanity = sanity) }
    }

    override fun updatePhase(phase: PhaseData) {
        _state.update { it.copy(phase = phase) }
    }

    override fun updateHuntWarning(warning: Boolean) {
        _state.update { it.copy(huntWarning = warning) }
    }

    override fun updateEvidence(evidence: List<EvidenceState>) {
        _state.update { it.copy(evidenceStates = evidence) }
    }

    override fun updateDifficulty(difficulty: DifficultyData) {
        _state.update { it.copy(difficulty = difficulty) }
    }

    override fun updateGhostDetails(ghostDetails: GhostDetails) {
        _state.update { it.copy(ghostDetails = ghostDetails) }
    }

    override fun updateMissionData(missionData: MissionData) {
        _state.update { it.copy(missionData = missionData) }
    }

    override fun updateOverrides(overrides: OperationOverrideData) {
        _state.update { it.copy(overrides = overrides) }
    }

    override fun updateWeather(weather: DifficultySettingResources.Weather) {
        _state.update { it.copy(weather = weather) }
    }

    override fun updateTemperature(temperature: TemperatureData) {
        _state.update { it.copy(temperature = temperature) }
    }

    override fun toggleGhostRejection(id: GhostResources.GhostIdentifier) {
        _state.update {
            it.copy(
                explicitRejections = if (it.explicitRejections.contains(id)) {
                    it.explicitRejections.minus(id)
                } else {
                    it.explicitRejections.plus(id)
                }
            )
        }
    }

    override fun reset() {
        _state.value = OperationData()
    }
}
