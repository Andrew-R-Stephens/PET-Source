package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.EvidenceDto

interface EvidenceDataSource {

    fun get(): Result<List<EvidenceDto>>

}