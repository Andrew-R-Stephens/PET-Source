package com.tritiumgaming.data.map.model.complex

class ComplexWorldMaps(
    val maps: List<ComplexWorldMap>
) {

    fun getMapById(id: String): ComplexWorldMap? = maps.find { map -> map.mapId == id }

    val shortenedMapNames = maps.map { map -> map.mapNameShort }
    fun orderRooms() = maps.forEach{ map -> map.orderRooms() }

}
