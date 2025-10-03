package com.tritiumgaming.data.evidence.source

import com.tritiumgaming.data.evidence.dto.EvidenceDto

interface EvidenceDataSource {

    fun get(): Result<List<EvidenceDto>>

}