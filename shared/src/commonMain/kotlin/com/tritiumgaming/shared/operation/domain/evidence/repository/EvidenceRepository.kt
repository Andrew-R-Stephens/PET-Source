package com.tritiumgaming.shared.operation.domain.evidence.repository

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType

interface EvidenceRepository {

    fun fetchEvidence(): Result<List<EvidenceType>>

}