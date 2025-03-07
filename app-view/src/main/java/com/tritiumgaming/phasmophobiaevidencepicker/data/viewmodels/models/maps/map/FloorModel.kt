package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map

import android.os.Build
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.Floor

class FloorModel {
    private var floorId: Int = 0
    var floorName: String? = null
        private set
    private var floorImage: String? = null

    var floorLayer: FloorLayerType

    val floorRooms: ArrayList<RoomModel> = ArrayList()
    val floorPOIs: ArrayList<PoiModel> = ArrayList()

    constructor(floor: Floor) {
        floorImage = floor.imageFile
        floorId = floor.floorId
        floorName = floor.floorName
        floorLayer = FloorLayerType.entries[floor.floorNumber]

        for (r in floor.floorRooms) {
            floorRooms.add(RoomModel(r.roomId, r.roomName, r.roomPoints))
        }
        for (p in floor.floorPOIs) {
            floorPOIs.add(PoiModel(p.poiId, p.poiName, p.poiType, p.x, p.y))
        }
    }

    constructor(layer: FloorLayerType) {
        this.floorLayer = layer
    }

    val floorRoomNames: ArrayList<String>
        get() {
            val names = ArrayList<String>()
            for (r in floorRooms) {
                names.add(r.name)
            }
            return names
        }

    val lastRoom: RoomModel?
        get() {
            if (floorRooms.isEmpty()) {
                return null
            }
            return floorRooms[floorRooms.size - 1]
        }

    fun getRoomById(id: Int): RoomModel? {
        for (room in floorRooms) {
            if (room.id == id) return room
        }

        return null
    }

    fun getRoomIndexById(id: Int): Int {
        var i = 0
        while (i < floorRooms.size) {
            if (floorRooms[i].id == id) return i
            i++
        }
        return ++i
    }

    override fun toString(): String {
        return "\n\t[Floor ID: $floorId] [Floor Name: $floorName] [ Assigned Layer: $floorLayer ] [Image File: $floorImage] Rooms:$floorRooms\n"
    }

    @Synchronized
    fun print() {
        Log.d(
            "Maps",
            "[Floor ID: $floorId] [Floor Name: $floorName] [ Assigned Layer: $floorLayer ] [Image File: $floorImage]"
        )
        for (r in floorRooms) {
            r.print()
        }

        for (p in floorPOIs) {
            p.print()
        }
    }

    fun orderRooms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            floorRooms.sortWith(Comparator.comparing(RoomModel::name))
        }
    }

    fun addRoomModels(roomModels: ArrayList<RoomModel>) {
        floorRooms.addAll(roomModels)
    }
}
