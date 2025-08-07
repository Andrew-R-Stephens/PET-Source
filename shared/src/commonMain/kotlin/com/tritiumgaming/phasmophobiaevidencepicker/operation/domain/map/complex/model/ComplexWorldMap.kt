package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

class ComplexWorldMap(
    val mapId: String,
    val mapName: String,
    val mapNameShort: String,
    val mapDimensions: ComplexWorldMapDimensions,
    val mapFloors: List<ComplexWorldMapFloor>
) {

    //var currentLayer: ComplexWorldMapFloorLayerType = ComplexWorldMapFloorLayerType.entries[0]

    /*val currentFloor: ComplexWorldMapFloor = mapFloors.find { floor ->
        floor.floorLayer == currentLayer } ?: mapFloors[0]*/

    fun getFloor(index: Int): ComplexWorldMapFloor = mapFloors[index]

    fun orderRooms() = mapFloors.forEach { floor -> floor.orderRooms() }

    override fun toString(): String {
        return "\n[Map ID: $mapId] [Map Name: $mapName, $mapNameShort] " +
                "[Dimensions: $mapDimensions] \nFloor Data:$mapFloors\n"
    }

}

