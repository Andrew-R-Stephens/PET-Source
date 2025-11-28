package com.tritiumgaming.shared.data.map.complex.model

class ComplexWorldMap(
    val mapId: String,
    val mapName: String,
    val mapNameShort: String,
    val mapDimensions: com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMapDimensions,
    val mapFloors: List<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMapFloor>
) {

    fun getFloor(index: Int): com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMapFloor = mapFloors[index]

    fun orderRooms() = mapFloors.forEach { floor -> floor.orderRooms() }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] " +
                "[Dimensions: $mapDimensions] \nFloor Data:$mapFloors\n"
    }

}

