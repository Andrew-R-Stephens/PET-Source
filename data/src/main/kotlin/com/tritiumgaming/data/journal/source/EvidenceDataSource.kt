package com.tritiumgaming.data.journal.source

import com.tritiumgaming.data.journal.dto.EvidenceDto

interface EvidenceDataSource {

    fun get(): Result<List<EvidenceDto>>

}