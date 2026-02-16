package com.tritiumgaming.shared.data.evidence.repository

import com.tritiumgaming.shared.data.evidence.model.Evidence
import com.tritiumgaming.shared.data.evidence.model.EvidenceType

interface EvidenceRepository {

    fun fetchEvidences(): Result<List<Evidence>>

    fun fetchEvidenceTypes(): Result<List<EvidenceType>>

}