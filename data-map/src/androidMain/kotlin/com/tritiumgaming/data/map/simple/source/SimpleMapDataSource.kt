package com.tritiumgaming.data.map.simple.source

import com.tritiumgaming.data.map.simple.dto.SimpleWorldMapDto

interface SimpleMapDataSource {

    fun fetchMaps(): Result<List<SimpleWorldMapDto>>

}