package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

interface EvidenceRepository {
    val localSource: EvidenceDataSource

    fun fetchEvidence(): List<EvidenceType>

}