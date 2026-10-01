package com.tritiumgaming.data.map.simple.repository

import com.tritiumgaming.data.map.simple.model.SimpleWorldMap

interface SimpleMapRepository {

    fun getMaps(): Result<List<SimpleWorldMap>>

}
