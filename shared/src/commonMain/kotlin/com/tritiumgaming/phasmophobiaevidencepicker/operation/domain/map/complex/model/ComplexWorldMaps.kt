package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

class ComplexWorldMaps(
    private val maps: List<ComplexWorldMap>
) {

    val shortenedMapNames = maps.map { map -> map.mapNameShort }

    fun getMapById(id: String): ComplexWorldMap? = maps.find { map -> map.mapId == id }

    fun orderRooms() = maps.forEach{ map -> map.orderRooms() }

}
