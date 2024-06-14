package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models

import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesBlueprint

class RoomModel(
    @JvmField var id: Int,
    var name: String,
    points: MapDesBlueprint.WorldMap.Floor.Room.RoomPoints?
) {

    @JvmField
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
