package com.tritiumgaming.shared.operation.domain.evidence.repository

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType

interface EvidenceRepository {

    fun fetchEvidence(): Result<List<EvidenceType>>

}