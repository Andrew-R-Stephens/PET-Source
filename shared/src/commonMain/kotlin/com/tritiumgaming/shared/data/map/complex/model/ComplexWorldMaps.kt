package com.tritiumgaming.shared.data.map.complex.model

class ComplexWorldMaps(
    private val maps: List<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMap>
) {

    fun getMapById(id: String): com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMap? = maps.find { map -> map.mapId == id }

    val shortenedMapNames = maps.map { map -> map.mapNameShort }
    fun orderRooms() = maps.forEach{ map -> map.orderRooms() }

}
