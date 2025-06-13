package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

class WorldMaps(
    private val maps: List<WorldMap>
) {

    val shortenedMapNames = maps.map { map -> map.mapNameShort }

    fun getMapById(id: String): WorldMap? = maps.find { map -> map.mapId == id }

    fun orderRooms() = maps.forEach{ map -> map.orderRooms() }

    @Synchronized
    fun print() = maps.forEach { map -> map.print() }

}
