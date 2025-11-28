package com.tritiumgaming.shared.data.evidence.repository

import com.tritiumgaming.shared.data.evidence.model.Evidence
import com.tritiumgaming.shared.data.evidence.model.EvidenceType

interface EvidenceRepository {

    fun fetchEvidence(): Result<List<Evidence>>

    fun fetchEvidenceType(): Result<List<EvidenceType>>

}