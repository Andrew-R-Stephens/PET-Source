package com.tritiumgaming.data.journal.source

import com.tritiumgaming.data.journal.dto.GhostDto

interface GhostDataSource {

    fun get(): Result<List<GhostDto>>

}