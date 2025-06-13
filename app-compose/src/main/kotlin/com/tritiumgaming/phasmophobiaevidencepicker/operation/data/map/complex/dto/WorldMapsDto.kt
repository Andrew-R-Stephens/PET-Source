package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.WorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.WorldMaps

class WorldMapsDto(
    internal var maps: List<WorldMapDto>
) {

    @Synchronized
    fun print() {
        for (m in maps) {
            m.print()
        }
    }

}

fun WorldMapsDto.toDomain() = WorldMaps(
    maps = maps.map { dto ->
        WorldMap(
            mapId = dto.mapId,
            mapName = dto.mapName,
            mapNameShort = dto.mapNameShort,
            mapDimensions = dto.mapDimensions.toDomain(),
            mapFloors = dto.mapFloors.toDomain()
        )
    }
)
