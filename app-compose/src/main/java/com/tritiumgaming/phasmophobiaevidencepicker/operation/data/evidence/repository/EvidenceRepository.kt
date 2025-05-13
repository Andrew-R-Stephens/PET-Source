package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.EvidenceLocalDataSource

class EvidenceRepository(
    localSource: EvidenceLocalDataSource
) {

    val evidenceList = localSource.evidenceList

}