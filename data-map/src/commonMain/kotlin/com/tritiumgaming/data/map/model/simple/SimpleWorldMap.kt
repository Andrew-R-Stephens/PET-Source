package com.tritiumgaming.data.map.model.simple

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapTitle

class SimpleWorldMap(
    val mapId: String,
    val mapName: MapTitle,
    val mapSize: MapSize,
    val thumbnailImage: MapThumbnail,
    val mapFloors: List<SimpleWorldMapFloor>,
    val defaultFloor: Int
) {

    //val allFloorLayers: ArrayList<ArrayList<Int>> = ArrayList()

    val floorCount: Int
        get() = mapFloors.size

    /*fun getFloorName(floorIndex: Int): MapFloorTitle {
        return mapFloors[floorIndex].layerName
    }*/

    fun getFloorImage(floorIndex: Int): MapFloorImage =
        mapFloors[floorIndex].image

    /*fun addFloorLayer(floorIndex: Int, layer: Int) {
        if (allFloorLayers.isEmpty() || floorIndex >= allFloorLayers.size) {
            val temp = ArrayList<Int>()
            temp.add(layer)
            allFloorLayers.add(temp)
        } else {
            allFloorLayers[floorIndex].add(layer)
        }
    }*/

    override fun toString(): String {
        return "SimpleWorldMap(mapId='$mapId', mapName=$mapName, mapSize=$mapSize, " +
                "thumbnailImage=$thumbnailImage, mapFloors=$mapFloors, " +
                "defaultFloor=$defaultFloor)\n"
    }

}