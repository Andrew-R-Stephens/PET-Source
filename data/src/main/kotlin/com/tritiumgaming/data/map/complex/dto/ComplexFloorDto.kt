package com.tritiumgaming.data.map.complex.dto

import android.graphics.PointF
import android.util.Log
import com.tritiumgaming.data.map.complex.mappers.WorldMapsSerializerDto
import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMapFloor

data class ComplexFloorDto(
    internal val floorId: Int,
    internal val floorName: String?,
    internal val floorImage: String?,
    internal val floorLayer: ComplexFloorLayerTypeDto,
    internal val floorRooms: List<ComplexRoomDto>,
    internal val floorPOIs: List<ComplexPoiDto>
) {

    constructor(floor: WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto) : this(
        floorId = floor.floorId,
        floorName = floor.floorName,
        floorImage = floor.imageFile,
        floorLayer = ComplexFloorLayerTypeDto.entries[floor.floorNumber],
        floorRooms = floor.floorRooms.map { roomSDto ->
            ComplexRoomDto(
                id = roomSDto.roomId,
                name = roomSDto.roomName,
                roomArea = ComplexRoomAreaDto(
                    points = roomSDto.roomPoints.points.map { pointDto ->
                        PointF(pointDto.x, pointDto.y)
                    }
                )
            )
        },
        floorPOIs = floor.floorPOIs.map { poiSDto ->
            ComplexPoiDto(
                id = poiSDto.poiId,
                name = poiSDto.poiName,
                type = poiSDto.poiType,
                x = poiSDto.x,
                y = poiSDto.y
            )
        }
    ) {
        floor.floorRooms.map { roomSDto ->
            ComplexRoomAreaDto(
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

fun List<ComplexFloorDto>.toDomain() = map { floorDto -> floorDto.toDomain() }

fun ComplexFloorDto.toDomain() = ComplexWorldMapFloor(
    floorId = floorId,
    floorName = floorName,
    floorImage = floorImage,
    floorLayer = floorLayer.toDomain(),
    floorRooms = floorRooms.toDomain(),
    floorPOIs = floorPOIs.toDomain()
)
