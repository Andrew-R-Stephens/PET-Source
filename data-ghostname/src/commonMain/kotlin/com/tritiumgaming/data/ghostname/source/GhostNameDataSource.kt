package com.tritiumgaming.data.ghostname.source

import com.tritiumgaming.data.ghostname.dto.GhostNameDto

interface GhostNameDataSource {

    fun get(): Result<List<GhostNameDto>>

}