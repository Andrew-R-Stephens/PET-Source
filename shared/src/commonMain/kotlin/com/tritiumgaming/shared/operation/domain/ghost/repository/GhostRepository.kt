package com.tritiumgaming.shared.operation.domain.ghost.repository

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.ghost.model.Ghost
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType

interface GhostRepository {

    fun fetchGhostTypes(): Result<List<GhostType>>

    fun fetchGhosts(): Result<List<Ghost>>

}