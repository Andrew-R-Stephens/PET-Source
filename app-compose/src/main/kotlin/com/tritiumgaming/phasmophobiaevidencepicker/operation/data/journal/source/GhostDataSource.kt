package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.GhostDto

interface GhostDataSource {

    fun get(): Result<List<GhostDto>>

}