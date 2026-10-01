package com.tritiumstudios.data.operation

import com.tritiumgaming.data.ghost.mapper.GhostResources
import com.tritiumgaming.data.journal.model.EvidenceState
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Weather
import com.tritiumstudios.data.operation.model.DifficultyData
import com.tritiumstudios.data.operation.model.GhostDetails
import com.tritiumstudios.data.operation.model.MapData
import com.tritiumstudios.data.operation.model.MissionData
import com.tritiumstudios.data.operation.model.OperationData
import com.tritiumstudios.data.operation.model.OperationOverrideData
import com.tritiumstudios.data.operation.model.PhaseData
import com.tritiumstudios.data.operation.model.SanityData
import com.tritiumstudios.data.operation.model.TemperatureData
import kotlinx.coroutines.flow.StateFlow

interface OperationRepository {
    val state: StateFlow<OperationData>

    fun updateMap(map: MapData)
    fun updateSanity(insanity: Float, sanity: Float)
    fun updateSanity(sanity: SanityData)
    fun updatePhase(phase: PhaseData)
    fun updateHuntWarning(warning: Boolean)
    fun updateEvidence(evidence: List<EvidenceState>)
    fun updateDifficulty(difficulty: DifficultyData)
    fun updateGhostDetails(ghostDetails: GhostDetails)
    fun updateMissionData(missionData: MissionData)
    fun updateOverrides(overrides: OperationOverrideData)
    fun updateWeather(weather: Weather)
    fun updateTemperature(temperature: TemperatureData)
    fun toggleGhostRejection(id: GhostResources.GhostIdentifier)

    fun reset()
}
