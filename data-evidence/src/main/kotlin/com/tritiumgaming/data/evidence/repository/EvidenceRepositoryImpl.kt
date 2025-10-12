package com.tritiumgaming.data.evidence.repository

import com.tritiumgaming.data.evidence.dto.toEvidenceType
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class EvidenceRepositoryImpl(
    val evidenceLocalDataSource: EvidenceDataSource
): EvidenceRepository {

    override fun fetchEvidence(): Result<List<EvidenceType>> {
        val result = evidenceLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toEvidenceType() }
    }

}