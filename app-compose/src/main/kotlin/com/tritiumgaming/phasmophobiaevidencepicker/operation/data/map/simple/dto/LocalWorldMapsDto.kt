package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.simple.LocalWorldMaps


class LocalWorldMapsDto(
    internal var maps: List<LocalWorldMapDto>
) {

    @Synchronized
    fun print() {
        for (m in maps) {
            m.print()
        }
    }

}

fun LocalWorldMapsDto.toDomain() = LocalWorldMaps(
    maps = maps.toDomain()
)
