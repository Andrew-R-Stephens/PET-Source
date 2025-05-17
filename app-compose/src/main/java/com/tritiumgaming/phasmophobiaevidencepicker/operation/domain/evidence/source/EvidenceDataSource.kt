package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

interface EvidenceDataSource {

    fun fetchEvidence(context: Context): List<EvidenceType>

}