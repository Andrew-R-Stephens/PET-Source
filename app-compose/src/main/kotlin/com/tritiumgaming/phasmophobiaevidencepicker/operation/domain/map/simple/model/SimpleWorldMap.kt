package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.SimpleWorldMapFloorDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources

class SimpleWorldMap(
    val mapId: String,
    val mapName: SimpleMapResources.MapTitle,
    val mapSize: SimpleMapResources.MapSize,
    val thumbnailImage: SimpleMapResources.MapThumbnail,
    val mapFloors: List<SimpleWorldMapFloorDto>,
    val defaultFloor: Int
) {

    private var defaultFloorIndex = 0
    var currentFloor: Int = 0 // TODO("Move to ViewModel")
    val floorNames = mutableListOf<Int>()
    val allFloorLayers: ArrayList<ArrayList<Int>> = ArrayList()

    val floorCount: Int
        get() = floorNames.size

    val floorName: Int
        get() = floorNames[currentFloor]

    fun addFloorLayer(floorIndex: Int, layer: Int) {
        if (allFloorLayers.isEmpty() || floorIndex >= allFloorLayers.size) {
            val temp = ArrayList<Int>()
            temp.add(layer)
            allFloorLayers.add(temp)
        } else {
            allFloorLayers[floorIndex].add(layer)
        }
    }

}