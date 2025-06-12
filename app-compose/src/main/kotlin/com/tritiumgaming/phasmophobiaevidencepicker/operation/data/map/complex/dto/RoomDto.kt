package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto

class RoomDto(
    var id: Int,
    var name: String,
    points: WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto.RoomPointsSerializerDto?
) {

    val roomArea: RoomAreaDto = RoomAreaDto()

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
