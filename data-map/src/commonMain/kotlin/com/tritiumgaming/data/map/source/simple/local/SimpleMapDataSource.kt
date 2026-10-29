package com.tritiumgaming.data.map.source.simple.local

import com.tritiumgaming.data.map.dto.simple.SimpleWorldMapDto

interface SimpleMapDataSource {

    fun fetchMaps(): Result<List<SimpleWorldMapDto>>

}