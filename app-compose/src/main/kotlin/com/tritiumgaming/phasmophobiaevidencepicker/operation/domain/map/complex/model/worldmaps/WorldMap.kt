package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

import android.util.Log

class WorldMap(
    val mapId: String,
    val mapName: String,
    val mapNameShort: String,
    val mapDimensions: MapDimensions,
    val mapFloors: List<Floor>
) {

    var currentLayer: FloorLayerType = FloorLayerType.entries[0]

    val currentFloor: Floor = mapFloors.find { floor ->
        floor.floorLayer == currentLayer } ?: mapFloors[0]

    fun getFloor(index: Int): Floor = mapFloors[index]

    fun orderRooms() = mapFloors.forEach { floor -> floor.orderRooms() }

    @Synchronized
    fun print() {
        Log.d("Maps", "$mapId $mapName $mapNameShort $mapDimensions")
        mapFloors.forEach { floor -> floor.print() }
    }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] [Dimensions: $mapDimensions] [Layer: $currentLayer] \nFloor Data:$mapFloors\n"
    }

}

