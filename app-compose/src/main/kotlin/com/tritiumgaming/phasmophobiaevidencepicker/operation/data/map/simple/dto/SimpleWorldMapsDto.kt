package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMaps

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
