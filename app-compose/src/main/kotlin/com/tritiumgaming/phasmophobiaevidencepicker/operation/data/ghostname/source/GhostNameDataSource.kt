package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.dto.GhostNameDto

interface GhostNameDataSource {

    fun get(): Result<List<GhostNameDto>>

}