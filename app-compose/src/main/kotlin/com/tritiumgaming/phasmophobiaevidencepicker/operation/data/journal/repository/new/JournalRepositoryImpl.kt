package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.new

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.GhostEvidenceDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toEvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toEvidenceTypeDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toGhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.GhostDataSource

class JournalRepositoryImpl(
    val ghostLocalDataSource: GhostDataSource,
    val evidenceLocalDataSource: EvidenceDataSource
): JournalRepository {

    override fun fetchGhosts(): Result<List<GhostType>> {
        val result = ghostLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toDomain() }
    }

    override fun fetchEvidence(): Result<List<EvidenceType>> {
        val result = evidenceLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toEvidenceType() }
    }

    override fun fetchGhostEvidence(): Result<List<GhostEvidence>> {

        val ghostsResult = ghostLocalDataSource.get()
        ghostsResult.exceptionOrNull()?.printStackTrace()
        val ghostList = ghostsResult.getOrDefault(emptyList())

        val evidenceResult = evidenceLocalDataSource.get()
        evidenceResult.exceptionOrNull()?.printStackTrace()
        val evidenceList = evidenceResult.getOrDefault(emptyList())

        val ghostEvidenceDtoList: List<GhostEvidenceDto> = ghostList.map { ghostDto ->

            val normalEvidence = ghostDto.normalEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }.toEvidenceTypeDto()
            }

            val strictEvidence = ghostDto.strictEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }.toEvidenceTypeDto()
            }

            GhostEvidenceDto(
                ghostDto = ghostDto,
                normalEvidences = normalEvidence,
                strictEvidences = strictEvidence
            )

        }

        return Result.success(ghostEvidenceDtoList.toGhostEvidence())

    }

}