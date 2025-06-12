package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto


class WorldMapDto {
    var mapId: String = ""
    var mapName: String = ""
    var mapNameShort: String = ""
    var mapDimensions: RoomDimensionsDto

    var currentLayer: FloorLayerTypeDto = FloorLayerTypeDto.entries[0]

    var mapFloors: ArrayList<FloorDto> = ArrayList()

    val currentFloor: FloorDto
        get() {
            for (floor in mapFloors) {
                if (floor.floorLayer == currentLayer) {
                    return floor
                }
            }
            return mapFloors[0]
        }

    constructor() {
        mapId = "undefined"
        mapName = "undefined"
        mapNameShort = "undefined"
        mapDimensions = RoomDimensionsDto(0, 0)
        for (layer in FloorLayerTypeDto.entries) mapFloors.add(FloorDto(layer))
    }

    constructor(worldMap: WorldMapsSerializerDto.WorldMapSerializerDto) {
        mapId = worldMap.mapId
        mapName = worldMap.mapName
        mapNameShort = worldMap.mapNameShort
        mapDimensions = RoomDimensionsDto(worldMap.mapDimensions.w, worldMap.mapDimensions.h)
        for (f in worldMap.mapFloors) {
            f.let { floorModel -> mapFloors.add(FloorDto(floorModel)) }
        }
        currentLayer =
            if (mapFloors.isNotEmpty()) { mapFloors[0].floorLayer }
            else FloorLayerTypeDto.FIRST_FLOOR
    }

    fun getFloor(index: Int): FloorDto {
        return mapFloors[index]
    }

    fun orderRooms() {
        for (f in mapFloors) {
            f.orderRooms()
        }
    }

    @Synchronized
    fun print() {
        Log.d("Maps", "$mapId $mapName $mapNameShort $mapDimensions")
        for (f in mapFloors) {
            f.print()
        }
    }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] [Dimensions: $mapDimensions] [Layer: $currentLayer] \nFloor Data:$mapFloors\n"
    }

}

/*
class MapModel {
    var mapId: Int
    var mapName: String = ""
    var mapNameShort: String = ""
    var mapDimensions: MapDimensionModel

    var currentLayer: FloorLayerType = FloorLayerType.entries[0]

    var mapFloors: ArrayList<FloorModel> = ArrayList()

    val currentFloor: FloorModel
        get() {
            for (floor in mapFloors) {
                if (floor.floorLayer == currentLayer) {
                    return floor
                }
            }
            return mapFloors[0]
        }

    constructor() {
        mapId = 0
        mapName = "undefined"
        mapNameShort = "undefined"
        mapDimensions = MapDimensionModel(0, 0)
        for (layer in FloorLayerType.entries) mapFloors.add(FloorModel(layer))
    }

    constructor(worldMap: MapDesBlueprint.WorldMap) {
        mapId = worldMap.mapId
        mapName = worldMap.mapName
        mapNameShort = worldMap.mapNameShort
        mapDimensions = MapDimensionModel(worldMap.mapDimensions.w, worldMap.mapDimensions.h)
        for (f in worldMap.mapFloors) {
            f.let { floorModel -> mapFloors.add(FloorModel(floorModel)) }
        }
        currentLayer =
            if (mapFloors.isNotEmpty()) { mapFloors[0].floorLayer }
            else FloorLayerType.FIRST_FLOOR
    }

    fun getFloor(index: Int): FloorModel {
        return mapFloors[index]
    }

    fun orderRooms() {
        for (f in mapFloors) {
            f.orderRooms()
        }
    }

    @Synchronized
    fun print() {
        Log.d("Maps", "$mapId $mapName $mapNameShort $mapDimensions")
        for (f in mapFloors) {
            f.print()
        }
    }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] [Dimensions: $mapDimensions] [Layer: $currentLayer] \nFloor Data:$mapFloors\n"
    }

}
*/
