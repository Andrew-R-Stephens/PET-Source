package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapSizeType

class LocalWorldMap(
    var mapId: String,
    var mapName: Int,
    var mapSize: WorldMapSizeType,
    @DrawableRes var thumbnailImage: Int
) {

    private var defaultFloorIndex = 0
    var currentFloor: Int = 0 // TODO("Move to ViewModel")
    val floorNames = mutableListOf<Int>()
    val allFloorLayers: ArrayList<ArrayList<Int>> = ArrayList()

    val floorCount: Int
        get() = floorNames.size

    var defaultFloor: Int
        get() = defaultFloorIndex
        set(index) {
            this.defaultFloorIndex = index
            this.currentFloor = this.defaultFloorIndex
        }

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
