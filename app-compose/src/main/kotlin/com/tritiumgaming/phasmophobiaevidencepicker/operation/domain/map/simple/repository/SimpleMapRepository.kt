package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMap

interface SimpleMapRepository {

    fun getMaps(): Result<List<SimpleWorldMap>>

}
