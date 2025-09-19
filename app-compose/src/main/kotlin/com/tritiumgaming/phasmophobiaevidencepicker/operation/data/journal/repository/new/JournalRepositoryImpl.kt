package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.new

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.GhostEvidenceDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toEvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toEvidenceTypeDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.toGhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.GhostDataSource
import com.tritiumgaming.shared.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.shared.operation.domain.journal.model.GhostType
import com.tritiumgaming.shared.operation.domain.journal.repository.JournalRepository

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

        Log.d("JournalRepositoryImpl", "Fetching GhostEvidence")

        val ghostsResult = ghostLocalDataSource.get()
        ghostsResult.exceptionOrNull()?.let { return Result.failure(it) }
        val ghostList = ghostsResult.getOrDefault(emptyList())
        Log.d("JournalRepositoryImpl", "Ghosts: ${ghostList.size}")

        val evidenceResult = evidenceLocalDataSource.get()
        evidenceResult.exceptionOrNull()?.let { return Result.failure(it) }
        val evidenceList = evidenceResult.getOrDefault(emptyList())
        Log.d("JournalRepositoryImpl", "Evidence: ${evidenceList.size}")

        Log.d("JournalRepositoryImpl", "Gathering GhostEvidence")

        val ghostEvidenceDtoList: List<GhostEvidenceDto> = ghostList.map { ghostDto ->

            val normalEvidence = ghostDto.normalEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }.toEvidenceTypeDto()
            }

            val strictEvidence = ghostDto.strictEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }.toEvidenceTypeDto()
            }

            val ghostEvidenceDto = GhostEvidenceDto(
                ghostDto = ghostDto,
                normalEvidences = normalEvidence,
                strictEvidences = strictEvidence
            )

            Log.d("JournalRepositoryImpl", "GhostEvidence: $ghostEvidenceDto")

            ghostEvidenceDto
        }

        return Result.success(ghostEvidenceDtoList.toGhostEvidence())

    }

}