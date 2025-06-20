package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

data class ComplexWorldMapFloor(
    internal val floorId: Int,
    internal val floorName: String?,
    internal val floorImage: String?,
    internal val floorLayer: ComplexWorldMapFloorLayerType,
    internal val floorRooms: List<ComplexWorldRoom>,
    internal val floorPOIs: List<ComplexWorldPoi>
) {

    val floorRoomNames: List<String>
        get() = floorRooms.map { it.name }

    val lastRoom: ComplexWorldRoom?
        get() {
            if (floorRooms.isEmpty()) { return null }
            return floorRooms[floorRooms.size - 1]
        }

    fun getRoomById(id: Int): ComplexWorldRoom? = floorRooms.find { room -> room.id == id }

    fun getRoomIndexById(id: Int): Int = floorRooms.indexOfFirst { room -> room.id == id }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[Floor ID: $floorId] [Floor Name: $floorName] " +
                "[ Assigned Layer: $floorLayer ] [Image File: $floorImage]")
        floorRooms.forEach { builder.append(it) }
        floorPOIs.forEach { builder.append(it) }

        return builder.toString()
    }

    fun orderRooms() {
        floorRooms.sortedBy { room -> room.name }
    }

}
