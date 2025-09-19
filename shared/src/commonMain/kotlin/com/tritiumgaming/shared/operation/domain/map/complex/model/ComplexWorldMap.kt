package com.tritiumgaming.shared.operation.domain.map.complex.model

class ComplexWorldMap(
    val mapId: String,
    val mapName: String,
    val mapNameShort: String,
    val mapDimensions: ComplexWorldMapDimensions,
    val mapFloors: List<ComplexWorldMapFloor>
) {

    fun getFloor(index: Int): ComplexWorldMapFloor = mapFloors[index]

    fun orderRooms() = mapFloors.forEach { floor -> floor.orderRooms() }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] " +
                "[Dimensions: $mapDimensions] \nFloor Data:$mapFloors\n"
    }

}

