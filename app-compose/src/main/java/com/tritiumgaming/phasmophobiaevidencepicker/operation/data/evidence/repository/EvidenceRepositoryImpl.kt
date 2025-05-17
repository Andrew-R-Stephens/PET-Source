package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

class EvidenceRepositoryImpl(
    context: Context,
    override val localSource: EvidenceDataSource
): EvidenceRepository {

    override val evidences = fetchEvidence(context)

    override fun fetchEvidence(context: Context): List<EvidenceType> {
        return localSource.fetchEvidence(context)
    }

    override fun getById(id: String): EvidenceType? {
        return evidences.find { it.id == id }
    }

}