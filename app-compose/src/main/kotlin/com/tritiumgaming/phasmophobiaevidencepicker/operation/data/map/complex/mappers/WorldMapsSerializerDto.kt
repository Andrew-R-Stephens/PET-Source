package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers

import android.graphics.PointF
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexFloorDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexFloorLayerTypeDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexMapDimensionsDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexPoiDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexRoomAreaDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexRoomDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexWorldMapDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.ComplexWorldMapsDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.POISerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto.RoomPointsSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto.RoomPointsSerializerDto.RoomPointSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.WorldDimensionsSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.poi.mappers.MapPoiResources

@Keep
class WorldMapsSerializerDto {
    @SerializedName("maps") val maps: ArrayList<Map<String, WorldMapSerializerDto>> = ArrayList()

    @Keep
    class WorldMapSerializerDto {
        @SerializedName("map_id") var mapId: String = ""
        @SerializedName("map_name") var mapName: String = ""
        @SerializedName("map_name_short") var mapNameShort: String = ""
        @SerializedName("map_floors") var mapFloors: ArrayList<FloorSerializerDto> = ArrayList()
        @SerializedName("map_dimensions") var mapDimensions: WorldDimensionsSerializerDto = WorldDimensionsSerializerDto()

        @Keep
        class WorldDimensionsSerializerDto {
            @SerializedName("w") var w: Int = 0
            @SerializedName("h") var h: Int = 0

            fun print() {
                Log.d("Map", "Map Dimensions:\t\tW: $w, H: $h")
            }
        }

        @Keep
        class FloorSerializerDto {
            @SerializedName("floor_id") var floorId: Int = 0
            @SerializedName("floor_number") var floorNumber: Int = 0
            @SerializedName("floor_name") var floorName: String = ""
            @SerializedName("image_file") var imageFile: String = ""
            @SerializedName("floor_pois") var floorPOIs: ArrayList<POISerializerDto> = ArrayList()
            @SerializedName("floor_rooms") var floorRooms: ArrayList<RoomSerializerDto> = ArrayList()

            @Keep
            class RoomSerializerDto {
                @SerializedName("room_iD") var roomId: Int = 0
                @SerializedName("room_name") var roomName: String = ""
                @SerializedName("room_points") var roomPoints: RoomPointsSerializerDto = RoomPointsSerializerDto()

                fun print() {
                    Log.d("Maps", "\t\t$roomId: $roomName")
                    roomPoints.print()
                }

                @Keep
                class RoomPointsSerializerDto {
                    @SerializedName("points") val points: ArrayList<RoomPointSerializerDto> = ArrayList()

                    fun print() { for (p in points) { p.print() } }

                    @Keep
                    class RoomPointSerializerDto {
                        @SerializedName("x") var x: Float = 0f
                        @SerializedName("y") var y: Float = 0f

                        fun print() {
                            Log.d("Maps", "\t\t\t\t[x: $x, y: $y]")
                        }
                    }
                }
            }

            @Keep
            class POISerializerDto {
                @SerializedName("poi_iD") var poiId: Int = 0
                @SerializedName("poi_name") var poiName: String = ""
                @SerializedName("poi_type") var poiType: Int = -1
                @SerializedName("x") var x: Float = 0f
                @SerializedName("y") var y: Float = 0f

                fun print() {
                    Log.d("Maps", "\t\t$poiId: $poiName, $poiType, x: $x y: $y") }
            }

            fun print() {
                Log.d("Maps",
                    "\t$floorNumber: $floorName Room Count: $floorRooms.size" +
                            "\n\tFloor image: $imageFile")
                for (r in floorRooms) { r.print() }
                for (p in floorPOIs) { p.print() }
            }
        }

        fun print() {
            Log.d("Maps", "$mapId : $mapName, ($mapNameShort), Floor Count: $mapFloors.size")
            mapDimensions.print()
            for (f in mapFloors) { f.print() }
        }
    }

    fun print() {
        for (m in maps) {
            val map = m["map_data"]
            map?.print()
        }
    }
}

fun WorldMapsSerializerDto.toMapList(): ComplexWorldMapsDto {
    try {
        return WorldMapsFactory.parseMinified(this).getOrThrow()
    }
    catch (e: JsonSyntaxException) {
        e.printStackTrace()
        return WorldMapsFactory.parseUnMinified(this).getOrThrow()
    }
}

fun List<POISerializerDto>.toPoiDto() = map {
    it.toPoiDto()
}

fun POISerializerDto.toPoiDto() = ComplexPoiDto(
    id = poiId,
    name = poiName,
    type = MapPoiResources.Poi.entries[poiType],
    point = PointF(x, y)
)

fun List<RoomPointSerializerDto>.toRoomPointDto() = map {
    it.toRoomPointDto()
}

fun RoomPointSerializerDto.toRoomPointDto() = PointF(x, y)

fun RoomPointsSerializerDto.toRoomAreaDto() = ComplexRoomAreaDto(
    points = points.toRoomPointDto()
)

fun List<RoomSerializerDto>.toRoomDto() = map {
    it.toRoomDto()
}

fun RoomSerializerDto.toRoomDto() = ComplexRoomDto(
    id = roomId,
    name = roomName,
    roomArea = roomPoints.toRoomAreaDto()
)

fun WorldDimensionsSerializerDto.toMapDimensionsDto() = ComplexMapDimensionsDto(
    w = w,
    h = h
)

fun FloorSerializerDto.toFloorDto() = ComplexFloorDto(
    floorId = floorId,
    floorName = floorName,
    floorImage = imageFile,
    floorLayer = ComplexFloorLayerTypeDto.entries[floorNumber],
    floorRooms = floorRooms.toRoomDto(),
    floorPOIs = floorPOIs.toPoiDto(),
)

fun List<WorldMapSerializerDto>.toWorldMapDto() = map {
    it.toWorldMapDto()
}

fun WorldMapSerializerDto.toWorldMapDto() = ComplexWorldMapDto(
    mapId = mapId,
    mapName = mapName,
    mapNameShort = mapNameShort,
    mapDimensions = mapDimensions.toMapDimensionsDto(),
    mapFloors = mapFloors.map { it.toFloorDto() }
)

fun WorldMapsSerializerDto.toWorldMapsDto() = ComplexWorldMapsDto(
    maps = maps.map { it.values }.flatten().toWorldMapDto()
)
