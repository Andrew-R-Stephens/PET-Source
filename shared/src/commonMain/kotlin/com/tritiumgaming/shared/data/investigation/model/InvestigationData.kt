package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

data class InvestigationData(
    val map: MapData = MapData(),
    val difficulty: DifficultyData = DifficultyData(),
    val sanity: SanityData = SanityData(),
    val phase: PhaseData = PhaseData(),
    val huntWarning: Boolean = false,
    val evidenceStates: List<EvidenceState> = emptyList(),
    //val ghostStates: List<GhostState> = emptyList(),
    val explicitRejections: Set<GhostResources.GhostIdentifier> = emptySet()
)
