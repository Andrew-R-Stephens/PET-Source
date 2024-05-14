package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models

import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper

class MapModel {
    @JvmField
    var mapId: Int
    @JvmField
    var mapName: String?
    @JvmField
    var mapDimensions: MapDimension
    @JvmField
    var currentLayer: FloorLayer? = FloorLayer.entries[0]

    @JvmField
    var mapFloors: ArrayList<FloorModel> = ArrayList()

    constructor() {
        mapId = 0
        mapName = "undefined"
        mapDimensions = MapDimension(0, 0)
        for (layer in FloorLayer.entries) mapFloors.add(FloorModel(layer))
    }

    constructor(worldMap: WorldMapWrapper.WorldMap) {
        mapId = worldMap.map_id
        mapName = worldMap.map_name
        mapDimensions = MapDimension(worldMap.map_dimensions.w, worldMap.map_dimensions.h)
        for (f in worldMap.map_floors) {
            mapFloors.add(
                FloorModel(f!!)
            )
        }
        currentLayer =
            if (mapFloors.isNotEmpty()) { mapFloors[0].floorLayer }
            else null
    }

    val currentFloor: FloorModel
        get() {
            for (floor in mapFloors) {
                if (floor.floorLayer == currentLayer) {
                    return floor
                }
            }
            return mapFloors[0]
        }

    fun setCurrentLayer(layer: FloorLayer): FloorLayer? {
        if (hasFloorAtLayer(layer)) {
            this.currentLayer = layer
        }

        return currentLayer
    }

    private fun hasFloorAtLayer(layer: FloorLayer): Boolean {
        for (floorModel in mapFloors) {
            if (floorModel.floorLayer == layer) {
                return true
            }
        }
        return false
    }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName] [Dimensions: $mapDimensions] [Layer: $currentLayer] \nFloor Data:$mapFloors\n"
    }

    fun getFloor(index: Int): FloorModel {
        return mapFloors[index]
    }

    @Synchronized
    fun print() {
        Log.d("Maps", "$mapId $mapName")
        mapDimensions.print()
        for (f in mapFloors) {
            f.print()
        }
    }

    fun orderRooms() {
        for (f in mapFloors) {
            f.orderRooms()
        }
    }
}
