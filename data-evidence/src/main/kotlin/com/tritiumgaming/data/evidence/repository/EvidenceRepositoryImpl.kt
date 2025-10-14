package com.tritiumgaming.data.evidence.repository

import com.tritiumgaming.data.evidence.dto.toDomain
import com.tritiumgaming.data.evidence.dto.toEvidenceType
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.shared.operation.domain.evidence.model.Evidence
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class EvidenceRepositoryImpl(
    val evidenceLocalDataSource: EvidenceDataSource
): EvidenceRepository {

    override fun fetchEvidenceType(): Result<List<EvidenceType>> {
        val result = evidenceLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toEvidenceType() }
    }

    override fun fetchEvidence(): Result<List<Evidence>> {
        val result = evidenceLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toDomain() }
    }

}