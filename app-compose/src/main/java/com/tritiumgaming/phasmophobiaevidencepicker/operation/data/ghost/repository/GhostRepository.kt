package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.GhostEvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostType

class GhostRepository(
    ghostLocalSource: GhostLocalDataSource,
    ghostEvidenceLocalSource: GhostEvidenceLocalDataSource
) {

    val ghosts = ghostLocalSource.ghosts
    val ghostEvidences = ghostEvidenceLocalSource.ghostEvidences

    init {
        ghosts.forEachIndexed { index: Int, ghost: GhostType ->
            val ghostEvidence = ghost.evidence
            ghostEvidence.normalEvidenceList.addAll(ghostEvidences[index].normalEvidences)
            ghostEvidence.strictEvidenceList.addAll(ghostEvidences[index].strictEvidences)
        }
    }

    fun getById(id: Int): GhostType? {
        return ghosts.find { it.id == id }
    }

}
