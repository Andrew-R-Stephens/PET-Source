package com.tritiumgaming.shared.data.investigation

import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.investigation.model.DifficultyData
import com.tritiumgaming.shared.data.investigation.model.InvestigationData
import com.tritiumgaming.shared.data.investigation.model.MapData
import kotlinx.coroutines.flow.StateFlow

interface InvestigationRepository {
    val state: StateFlow<InvestigationData>

    fun updateMap(map: MapData)
    fun updateSanity(insanity: Float, sanity: Float)
    fun updateEvidence(evidence: List<EvidenceState>)

    //fun updateGhostStates(ghosts: List<GhostState>)
    fun updateDifficulty(difficulty: DifficultyData)
    fun toggleGhostRejection(id: GhostResources.GhostIdentifier)
    fun reset()
}
