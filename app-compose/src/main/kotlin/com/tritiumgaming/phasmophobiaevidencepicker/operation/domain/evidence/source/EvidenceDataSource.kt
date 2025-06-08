package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

interface EvidenceDataSource {

    fun fetchEvidence(): Result<List<EvidenceType>>

}