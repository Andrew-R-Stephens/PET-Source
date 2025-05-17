package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

interface EvidenceRepository {

    val localSource: EvidenceDataSource
    val evidences: List<EvidenceType>

    fun fetchEvidence(context: Context): List<EvidenceType>
    fun getById(id: String): EvidenceType?

}