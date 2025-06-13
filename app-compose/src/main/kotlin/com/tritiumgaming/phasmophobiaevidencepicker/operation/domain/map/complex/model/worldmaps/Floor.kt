package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

import android.util.Log

data class Floor(
    internal val floorId: Int,
    internal val floorName: String?,
    internal val floorImage: String?,
    internal val floorLayer: FloorLayerType,
    internal val floorRooms: List<Room>,
    internal val floorPOIs: List<Poi>
) {

    val floorRoomNames: List<String>
        get() = floorRooms.map { it.name }

    val lastRoom: Room?
        get() {
            if (floorRooms.isEmpty()) { return null }
            return floorRooms[floorRooms.size - 1]
        }

    fun getRoomById(id: Int): Room? = floorRooms.find { room -> room.id == id }

    fun getRoomIndexById(id: Int): Int = floorRooms.indexOfFirst { room -> room.id == id }

    override fun toString(): String {
        return "\n\t[Floor ID: $floorId] [Floor Name: $floorName] [ Assigned Layer: $floorLayer ] [Image File: $floorImage] Rooms:$floorRooms\n"
    }

    @Synchronized
    fun print() {
        Log.d("Maps",
            "[Floor ID: $floorId] [Floor Name: $floorName] " +
                    "[ Assigned Layer: $floorLayer ] [Image File: $floorImage]"
        )
        floorRooms.forEach { it.print() }
        floorPOIs.forEach { it.print() }
    }

    fun orderRooms() {
        floorRooms.sortedBy { room -> room.name }
    }

}
