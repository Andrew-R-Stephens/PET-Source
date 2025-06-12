package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.os.Build
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto

class FloorDto {
    private var floorId: Int = 0
    var floorName: String? = null
        private set
    private var floorImage: String? = null

    var floorLayer: FloorLayerTypeDto

    val floorRooms: ArrayList<RoomDto> = ArrayList()
    val floorPOIs: ArrayList<PoiDto> = ArrayList()

    constructor(floor: WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto) {
        floorImage = floor.imageFile
        floorId = floor.floorId
        floorName = floor.floorName
        floorLayer = FloorLayerTypeDto.entries[floor.floorNumber]

        for (r in floor.floorRooms) {
            floorRooms.add(RoomDto(r.roomId, r.roomName, r.roomPoints))
        }
        for (p in floor.floorPOIs) {
            floorPOIs.add(PoiDto(p.poiId, p.poiName, p.poiType, p.x, p.y))
        }
    }

    constructor(layer: FloorLayerTypeDto) {
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

    val lastRoom: RoomDto?
        get() {
            if (floorRooms.isEmpty()) {
                return null
            }
            return floorRooms[floorRooms.size - 1]
        }

    fun getRoomById(id: Int): RoomDto? {
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
            floorRooms.sortWith(Comparator.comparing(RoomDto::name))
        }
    }

    fun addRoomModels(roomModels: ArrayList<RoomDto>) {
        floorRooms.addAll(roomModels)
    }
}
