package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

import android.util.Log

class ComplexWorldRoom(
    val id: Int,
    val name: String,
    val roomArea: ComplexWorldRoomArea
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
