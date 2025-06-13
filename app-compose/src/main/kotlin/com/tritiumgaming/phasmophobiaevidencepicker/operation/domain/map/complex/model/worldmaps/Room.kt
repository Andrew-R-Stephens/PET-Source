package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

import android.util.Log

class Room(
    val id: Int,
    val name: String,
    val roomArea: RoomArea
) {

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room points: $roomArea]"
    }

    @Synchronized
    fun print() {
        Log.d("Maps", "$id $name")
        roomArea.print()
    }
}
