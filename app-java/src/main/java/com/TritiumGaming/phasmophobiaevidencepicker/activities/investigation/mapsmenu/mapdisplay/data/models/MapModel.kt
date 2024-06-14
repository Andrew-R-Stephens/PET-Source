package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models

import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesBlueprint

class MapModel {
    @JvmField
    var mapId: Int
    @JvmField
    var mapName: String = ""
    @JvmField
    var mapNameShort: String = ""
    @JvmField
    var mapDimensions: MapDimensionModel

    var currentLayer: FloorLayerType = FloorLayerType.entries[0]

    @JvmField
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
        mapId = worldMap.map_id
        mapName = worldMap.map_name
        mapNameShort = worldMap.map_name_short
        mapDimensions = MapDimensionModel(worldMap.map_dimensions.w, worldMap.map_dimensions.h)
        for (f in worldMap.map_floors) {
            mapFloors.add(
                FloorModel(f!!)
            )
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
        Log.d("Maps", "$mapId $mapName")
        mapDimensions.print()
        for (f in mapFloors) {
            f.print()
        }
    }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName] [Dimensions: $mapDimensions] [Layer: $currentLayer] \nFloor Data:$mapFloors\n"
    }

}
