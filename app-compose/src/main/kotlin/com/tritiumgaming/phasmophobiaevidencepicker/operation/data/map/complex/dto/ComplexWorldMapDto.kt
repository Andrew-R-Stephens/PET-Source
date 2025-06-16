package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto

data class ComplexWorldMapDto(
    val mapId: String,
    val mapName: String,
    val mapNameShort: String,
    val mapDimensions: ComplexMapDimensionsDto,
    val mapFloors: List<ComplexFloorDto>
) {

    constructor(worldMap: WorldMapsSerializerDto.WorldMapSerializerDto) : this(
        mapId = worldMap.mapId,
        mapName = worldMap.mapName,
        mapNameShort = worldMap.mapNameShort,
        mapDimensions = ComplexMapDimensionsDto(worldMap.mapDimensions.w, worldMap.mapDimensions.h),
        mapFloors = worldMap.mapFloors.map { floorDto -> ComplexFloorDto(floorDto) }
    )

    @Synchronized
    fun print() {
        Log.d("Maps", "$mapId $mapName $mapNameShort $mapDimensions")
        for (f in mapFloors) {
            f.print()
        }
    }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] [Dimensions: $mapDimensions] \nFloor Data:$mapFloors\n"
    }

}
