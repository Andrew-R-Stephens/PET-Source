package com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint

class RoomModel(
    var id: Int,
    var name: String,
    points: MapDesBlueprint.WorldMap.Floor.Room.RoomPoints?
) {

    val roomArea: RoomAreaModel = RoomAreaModel()

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
