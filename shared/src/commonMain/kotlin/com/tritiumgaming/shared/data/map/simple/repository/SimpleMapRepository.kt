package com.tritiumgaming.shared.data.map.simple.repository

import com.tritiumgaming.shared.data.map.simple.model.SimpleWorldMap

interface SimpleMapRepository {

    fun getMaps(): Result<List<SimpleWorldMap>>

}
