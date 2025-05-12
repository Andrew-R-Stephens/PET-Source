package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.maps.mapviewer

import androidx.annotation.DrawableRes

class MapInteractModel {

    var mapId: Int = 0
    var mapName: Int = 0
    var mapSize: Int = 0

    @DrawableRes var thumbnailImage: Int = 0
        @DrawableRes get

    private var defaultFloorIndex = 0
    var currentFloor: Int = 0
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
