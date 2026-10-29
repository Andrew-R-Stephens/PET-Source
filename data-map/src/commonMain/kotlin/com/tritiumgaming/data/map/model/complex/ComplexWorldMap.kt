package com.tritiumgaming.data.map.model.complex

class ComplexWorldMap(
    val mapId: String,
    val mapName: String,
    val mapNameShort: String,
    val mapDimensions: ComplexWorldMapDimensions,
    val mapFloors: List<ComplexWorldMapFloor>
) {

    fun getFloor(id: String): ComplexWorldMapFloor {
        println("ComplexWorldMap getFloor: $id vs ${ mapFloors.map { it.floorId.toString() }}")
        return mapFloors.first { it.floorId.toString() == id }
    }
    fun getFloorIndex(id: String): Int = mapFloors.indexOfFirst{it.floorId.toString() == id}

    fun orderRooms() = mapFloors.forEach { floor -> floor.orderRooms() }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] " +
                "[Dimensions: $mapDimensions] \nFloor Data:$mapFloors\n"
    }

}

