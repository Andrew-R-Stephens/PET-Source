package com.tritiumgaming.data.map.model.worldmap

class WorldMaps(
    val worldMaps: Map<String, WorldMap> = emptyMap()
) {
    fun getMap(id: String) = worldMaps[id]
}
