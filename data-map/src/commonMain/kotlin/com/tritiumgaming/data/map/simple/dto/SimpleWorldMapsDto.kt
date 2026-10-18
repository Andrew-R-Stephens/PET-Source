package com.tritiumgaming.data.map.simple.dto

import com.tritiumgaming.data.map.simple.model.SimpleWorldMaps

data class SimpleWorldMapsDto(
    internal var maps: List<SimpleWorldMapDto>
)

fun SimpleWorldMapsDto.toDomain() = SimpleWorldMaps(
    maps = maps.toDomain()
)
