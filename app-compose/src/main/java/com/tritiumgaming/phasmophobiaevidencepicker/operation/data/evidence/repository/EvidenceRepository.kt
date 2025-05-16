package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType

class EvidenceRepository(
    context: Context,
    private val localSource: EvidenceLocalDataSource
) {

    val evidences = fetchEvidence(context)

    private fun fetchEvidence(context: Context): List<EvidenceType> {
        return localSource.fetchEvidence(context)
    }

    fun getById(id: String): EvidenceType? {
        return evidences.find { it.id == id }
    }

}