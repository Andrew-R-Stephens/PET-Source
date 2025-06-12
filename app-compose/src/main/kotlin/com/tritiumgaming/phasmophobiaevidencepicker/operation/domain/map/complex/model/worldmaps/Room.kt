package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto

class Room(
    var id: Int,
    var name: String,
    points: WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto.RoomPointsSerializerDto?
) {

    val roomArea: RoomArea = RoomArea()

    init {
        roomArea.setPoints(points)
    }

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room points: $roomArea]"
    }

    @Synchronized
    fun print() {
        Log.d("Maps", "$id $name")
        roomArea.print()
    }
}
