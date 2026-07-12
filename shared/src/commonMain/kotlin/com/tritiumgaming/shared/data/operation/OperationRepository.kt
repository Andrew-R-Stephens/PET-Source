package com.tritiumgaming.shared.data.operation

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.operation.model.DifficultyData
import com.tritiumgaming.shared.data.operation.model.EvidenceState
import com.tritiumgaming.shared.data.operation.model.GhostDetails
import com.tritiumgaming.shared.data.operation.model.MapData
import com.tritiumgaming.shared.data.operation.model.MissionData
import com.tritiumgaming.shared.data.operation.model.OperationData
import com.tritiumgaming.shared.data.operation.model.OperationOverrideData
import com.tritiumgaming.shared.data.operation.model.PhaseData
import com.tritiumgaming.shared.data.operation.model.SanityData
import com.tritiumgaming.shared.data.operation.model.TemperatureData
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
