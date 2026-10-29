package com.tritiumgaming.data.map.dto.simple

import com.tritiumgaming.data.map.model.simple.SimpleWorldMaps

data class SimpleWorldMapsDto(
    internal var maps: List<SimpleWorldMapDto>
)

fun SimpleWorldMapsDto.toDomain() = SimpleWorldMaps(
    maps = maps.toDomain()
)
