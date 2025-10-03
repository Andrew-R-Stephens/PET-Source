package com.tritiumgaming.data.map.complex.dto

import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMap
import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMaps

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
