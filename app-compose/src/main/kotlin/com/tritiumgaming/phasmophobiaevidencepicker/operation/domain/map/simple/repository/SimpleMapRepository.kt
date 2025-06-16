package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.simple.LocalWorldMap

interface SimpleMapRepository {

    fun getMaps(): Result<List<LocalWorldMap>>
    fun getThumbnails(): Result<List<Int>>

}
