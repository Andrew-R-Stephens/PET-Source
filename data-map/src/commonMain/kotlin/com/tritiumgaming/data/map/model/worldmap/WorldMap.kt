package com.tritiumgaming.data.map.model.worldmap

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.data.map.model.complex.ComplexWorldMap
import com.tritiumgaming.data.map.model.complex.ComplexWorldMapDimensions
import com.tritiumgaming.data.map.model.simple.SimpleWorldMap

class WorldMap(
    val id: String = "",
    val simpleMap: SimpleWorldMap = simpleDefault,
    val complexMap: ComplexWorldMap = complexDefault
) {
    val defaultFloorIndex: Int = simpleMap.defaultFloor

    companion object {
        val simpleDefault = SimpleWorldMap(
            mapId = "",
            mapName = MapTitle.BLEASDALE_FARMHOUSE,
            mapSize = MapSize.SMALL,
            thumbnailImage = MapThumbnail.BLEASDALE_FARMHOUSE,
            mapFloors = emptyList(),
            defaultFloor = 0
        )

        val complexDefault = ComplexWorldMap(
            mapId = "",
            mapName = "",
            mapNameShort = "",
            mapDimensions = ComplexWorldMapDimensions(0, 0),
            mapFloors = emptyList()
        )
    }
}
