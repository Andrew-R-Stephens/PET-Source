package com.tritiumgaming.data.ghost.source

import com.tritiumgaming.data.ghost.dto.GhostDto

interface GhostDataSource {

    fun get(): Result<List<GhostDto>>

}