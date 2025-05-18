package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

class EvidenceRepositoryImpl(
    override val localSource: EvidenceDataSource
): EvidenceRepository {

    override fun fetchEvidence(): List<EvidenceType> {
        return localSource.fetchEvidence()
    }

}