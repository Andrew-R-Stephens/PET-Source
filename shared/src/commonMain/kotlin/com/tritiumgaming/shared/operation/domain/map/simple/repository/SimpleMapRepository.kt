package com.tritiumgaming.shared.operation.domain.map.simple.repository

import com.tritiumgaming.shared.operation.domain.map.simple.model.SimpleWorldMap

interface SimpleMapRepository {

    fun getMaps(): Result<List<SimpleWorldMap>>

}
