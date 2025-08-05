package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMap

class InteractiveWorldMap(
    val simpleWorldMap: SimpleWorldMap,
    val complexWorldMap: ComplexWorldMap
) {

    val mapName: SimpleMapResources.MapTitle
        get() = simpleWorldMap.mapName

    val thumbnailImage: SimpleMapResources.MapThumbnail
        get() = simpleWorldMap.thumbnailImage

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

    private var defaultFloorIndex = 0
    var currentFloor: Int = 0
    val currentFloorImage: SimpleMapResources.MapFloorImage
        get() = simpleWorldMap.mapFloors.get(currentFloor).image

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