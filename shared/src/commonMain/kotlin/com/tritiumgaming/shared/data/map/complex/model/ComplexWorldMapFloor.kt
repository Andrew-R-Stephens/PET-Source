package com.tritiumgaming.shared.data.map.complex.model

data class ComplexWorldMapFloor(
    internal val floorId: Int,
    internal val floorName: String?,
    internal val floorImage: String?,
    internal val floorLayer: com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMapFloorLayerType,
    internal val floorRooms: List<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldRoom>,
    internal val floorPOIs: List<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldPoi>
) {

    val floorRoomNames: List<String>
        get() = floorRooms.map { it.name }

    val lastRoom: com.tritiumgaming.shared.data.map.complex.model.ComplexWorldRoom?
        get() {
            if (floorRooms.isEmpty()) { return null }
            return floorRooms[floorRooms.size - 1]
        }

    val rooms: List<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldRoom>
        get() = floorRooms

    fun getRoomById(id: Int): com.tritiumgaming.shared.data.map.complex.model.ComplexWorldRoom? = floorRooms.find { room -> room.id == id }

    fun getRoomIndexById(id: Int): Int = floorRooms.indexOfFirst { room -> room.id == id }

    fun getPois(): List<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldPoi> = floorPOIs

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
