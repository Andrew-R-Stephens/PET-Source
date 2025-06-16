package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.graphics.PointF
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.complex.Floor

class FloorDto(
    internal val floorId: Int,
    internal val floorName: String?,
    internal val floorImage: String?,
    internal val floorLayer: FloorLayerTypeDto,
    internal val floorRooms: List<RoomDto>,
    internal val floorPOIs: List<PoiDto>
) {

    constructor(floor: WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto) : this(
        floorId = floor.floorId,
        floorName = floor.floorName,
        floorImage = floor.imageFile,
        floorLayer = FloorLayerTypeDto.entries[floor.floorNumber],
        floorRooms = floor.floorRooms.map { roomSDto ->
            RoomDto(
                id = roomSDto.roomId,
                name = roomSDto.roomName,
                roomArea = RoomAreaDto(
                    points = roomSDto.roomPoints.points.map { pointDto ->
                        PointF(pointDto.x, pointDto.y)
                    }
                )
            )
        },
        floorPOIs = floor.floorPOIs.map { poiSDto ->
            PoiDto(poiSDto.poiId, poiSDto.poiName, poiSDto.poiType, poiSDto.x, poiSDto.y)
        }
    ) {
        floor.floorRooms.map { roomSDto ->
            RoomAreaDto(
                points = roomSDto.roomPoints.points.map { pointDto ->
                    PointF(pointDto.x, pointDto.y)
                }
            )

        }
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

}

fun List<FloorDto>.toDomain() = map { floorDto -> floorDto.toDomain() }

fun FloorDto.toDomain() = Floor(
    floorId = floorId,
    floorName = floorName,
    floorImage = floorImage,
    floorLayer = floorLayer.toDomain(),
    floorRooms = floorRooms.toDomain(),
    floorPOIs = floorPOIs.toDomain()
)
