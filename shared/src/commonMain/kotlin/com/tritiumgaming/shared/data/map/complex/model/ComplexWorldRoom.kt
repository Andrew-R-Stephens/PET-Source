package com.tritiumgaming.shared.data.map.complex.model

class ComplexWorldRoom(
    val id: Int,
    val name: String,
    val roomArea: ComplexWorldRoomArea
) {

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room points: $roomArea]"
    }

}
