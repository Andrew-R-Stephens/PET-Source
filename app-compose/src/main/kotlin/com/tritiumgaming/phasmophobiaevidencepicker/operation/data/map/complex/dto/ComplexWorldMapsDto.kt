package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldMaps

data class ComplexWorldMapsDto(
    internal var maps: List<ComplexWorldMapDto>
) {

    @Synchronized
    fun print() {
        for (m in maps) {
            m.print()
        }
    }

}

fun ComplexWorldMapsDto.toDomain() = ComplexWorldMaps(
    maps = maps.map { dto ->
        ComplexWorldMap(
            mapId = dto.mapId,
            mapName = dto.mapName,
            mapNameShort = dto.mapNameShort,
            mapDimensions = dto.mapDimensions.toDomain(),
            mapFloors = dto.mapFloors.toDomain()
        )
    }
)
