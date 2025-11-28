package com.tritiumgaming.data.map.simple.dto

import com.tritiumgaming.shared.data.map.simple.model.SimpleWorldMaps

data class SimpleWorldMapsDto(
    internal var maps: List<SimpleWorldMapDto>
) {

    @Synchronized
    fun print() {
        for (m in maps) {
            m.print()
        }
    }

}

fun SimpleWorldMapsDto.toDomain() = SimpleWorldMaps(
    maps = maps.toDomain()
)
