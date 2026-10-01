package com.tritiumgaming.data.evidence.repository

import com.tritiumgaming.data.evidence.model.Evidence
import com.tritiumgaming.data.evidence.model.EvidenceType

interface EvidenceRepository {

    fun fetchEvidences(): Result<List<Evidence>>

    fun fetchEvidenceTypes(): Result<List<EvidenceType>>

}