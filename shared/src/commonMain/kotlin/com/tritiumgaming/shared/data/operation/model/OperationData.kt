package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

data class OperationData(
    val map: MapData = MapData(),
    val difficulty: DifficultyData = DifficultyData(),
    val sanity: SanityData = SanityData(),
    val phase: PhaseData = PhaseData(),
    val huntWarning: Boolean = false,
    val evidenceStates: List<EvidenceState> = emptyList(),
    //val ghostStates: List<GhostState> = emptyList(),
    val explicitRejections: Set<GhostResources.GhostIdentifier> = emptySet()
)
