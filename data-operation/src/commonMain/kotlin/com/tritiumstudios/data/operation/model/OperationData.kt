package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.data.journal.model.EvidenceState
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Weather

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
    val explicitRejections: Set<GhostIdentifier> = emptySet(),
    val ghostDetails: GhostDetails = GhostDetails(),
    val missionData: MissionData = MissionData()
)
