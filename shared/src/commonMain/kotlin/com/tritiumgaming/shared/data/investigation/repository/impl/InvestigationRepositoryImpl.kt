package com.tritiumgaming.shared.data.investigation.repository.impl

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.DifficultyData
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.InvestigationData
import com.tritiumgaming.shared.data.investigation.model.MapData
import com.tritiumgaming.shared.data.investigation.model.SanityData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InvestigationRepositoryImpl : InvestigationRepository {

    private val _state = MutableStateFlow(InvestigationData())
    override val state = _state.asStateFlow()

    override fun updateMap(map: MapData) {
        _state.update { it.copy(map = map) }
    }

    override fun updateSanity(insanity: Float, sanity: Float) {
        _state.update { it.copy(sanity = SanityData(insanity, sanity)) }
    }

    override fun updateEvidence(evidence: List<EvidenceState>) {
        _state.update { it.copy(evidenceStates = evidence) }
    }

    override fun updateDifficulty(difficulty: DifficultyData) {
        _state.update { it.copy(difficulty = difficulty) }
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
        _state.value = InvestigationData()
    }
}