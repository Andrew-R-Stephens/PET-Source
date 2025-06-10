package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.new

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.GhostEvidenceDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

//TODO "Not yet implemented"
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

        return result.map { it.toDomain() }
    }

    override fun fetchGhostEvidence(): Result<List<GhostEvidence>> {

        val ghostsResult = ghostLocalDataSource.get()
        ghostsResult.exceptionOrNull()?.printStackTrace()
        val ghostList = ghostsResult.getOrDefault(emptyList())

        val evidenceResult = evidenceLocalDataSource.get()
        evidenceResult.exceptionOrNull()?.printStackTrace()
        val evidenceList = evidenceResult.getOrDefault(emptyList())

        val ghostEvidenceDtolist: List<GhostEvidenceDto> = ghostList.map { ghostDto ->

            val normalEvidence = ghostDto.normalEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }.toLocal()
            }

            val strictEvidence = ghostDto.strictEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }.toLocal()
            }

            GhostEvidenceDto(
                ghostDto = ghostDto,
                normalEvidences = normalEvidence,
                strictEvidences = strictEvidence
            )

        }

        return Result.success(ghostEvidenceDtolist.toDomain())

    }

}