package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

data class OperationData(
    val map: MapData = MapData(),
    val difficulty: DifficultyData = DifficultyData(),
    val overrides: OperationOverrideData = OperationOverrideData(),
    val sanity: SanityData = SanityData(),
    val phase: PhaseData = PhaseData(),
    val weather: Weather = Weather.RANDOM,
    val temperature: TemperatureData = TemperatureData(),
    val huntWarning: Boolean = false,
    val evidenceStates: List<EvidenceState> = emptyList(),
    val explicitRejections: Set<GhostResources.GhostIdentifier> = emptySet(),
    val ghostDetails: GhostDetails = GhostDetails(),
    val missionData: MissionData = MissionData()
)
