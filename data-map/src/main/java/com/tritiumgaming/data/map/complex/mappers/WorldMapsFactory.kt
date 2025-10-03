package com.tritiumgaming.data.map.complex.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tritiumgaming.data.map.complex.dto.ComplexWorldMapDto
import com.tritiumgaming.data.map.complex.dto.ComplexWorldMapsDto
import com.tritiumgaming.data.map.complex.mappers.WorldMapsSerializerDto.WorldMapSerializerDto.WorldDimensionsSerializerDto

object WorldMapsFactory {

    fun parseMinified(worldMaps: WorldMapsSerializerDto): Result<ComplexWorldMapsDto> {

        //Log.d("Map", "Blueprint: \n$mapDesBlueprint")

        val worldMapDtos = mutableListOf<ComplexWorldMapDto>()

        for (mappedWorldMap in worldMaps.maps) {
            // ---

            val mappedWorldMapJSON = getJSON(mappedWorldMap)
            val typeWorldMap = object : TypeToken<Map<String?, WorldMapsSerializerDto.WorldMapSerializerDto?>?>() {}.type
            val serializedWorldMap = Gson().fromJson<Map<String, WorldMapsSerializerDto.WorldMapSerializerDto>>(mappedWorldMapJSON, typeWorldMap)

            val mapJSON = getJSON(serializedWorldMap["map_data"])

            val typeWorldMapData = object : TypeToken<Map<String, Any>>() {}.type
            val serializedWorldMapData: Map<String, Any> = Gson().fromJson(mapJSON, typeWorldMapData)

            // ---

            // Dimensions
            val dimensionJSON = getJSON(serializedWorldMapData["map_dimensions"])
            //Log.d("Dimension", dimensionJSON)
            val typeWorldMapDimensions = object : TypeToken<Map<String?, Any>?>() {}.type
            val serializedWorldMapDimensions =
                Gson().fromJson<Map<String, WorldDimensionsSerializerDto>>(dimensionJSON, typeWorldMapDimensions)

            val worldMapDimensionSerializerDto = WorldDimensionsSerializerDto()
            worldMapDimensionSerializerDto.w = getInt(serializedWorldMapDimensions["w"])
            worldMapDimensionSerializerDto.h = getInt(serializedWorldMapDimensions["h"])

            //--

            // All Floors
            val floorsJSON = getJSON(serializedWorldMapData["map_floors"])
            //Log.d("Floors", floorsJSON)
            val typeFloors = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
            val serializedFloors = Gson().fromJson<ArrayList<Map<String?, Any>>>(floorsJSON, typeFloors)

            val serialFloorDtos = ArrayList<WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto>()
            for (serializedFloor in serializedFloors) {

                // Floor    "floor_id" "floor_number" "floor_pois" "floor_rooms"
                val floorJSON = getJSON(serializedFloor)

                val typeFloorData = object : TypeToken<Map<String?, Any>?>() {}.type
                val serializedFloorData = Gson().fromJson<Map<String, Any>>(floorJSON, typeFloorData)

                val floorId = serializedFloorData["floor_id"].toString()
                val floorName = serializedFloorData["floor_name"].toString()
                val floorNumber = serializedFloorData["floor_number"].toString()
                val roomsJSON = getJSON(serializedFloorData["floor_rooms"])
                val floorPOIsJSON = getJSON(serializedFloorData["floor_pois"])


                // All Rooms
                val typeRooms = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
                val serializedRooms = Gson().fromJson<ArrayList<Map<String, Any>>>(roomsJSON, typeRooms)
                //Log.d("Rooms", roomsJSON)

                val roomsDtos = ArrayList<WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto>()
                for (serializedRoom in serializedRooms) {
                    // "room_iD" "room_name" "room_points"

                    val roomDto = WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto()

                    val roomID = getInt(serializedRoom["room_iD"])
                    val roomName = getString(serializedRoom["room_name"])
                    val roomPointsListJSON = getJSON(serializedRoom["room_points"])
                    //Log.d("RoomPoints", pointsListJSON)


                    // All Points
                    //"points" "x": "y":
                    val typeRoomPoints = object : TypeToken<Map<String?, Any>?>() {}.type
                    val serializedRoomPoints =
                        Gson().fromJson<Map<String, *>>(roomPointsListJSON, typeRoomPoints)

                    val roomPointsJSON = getJSON(serializedRoomPoints["points"])

                    //Log.d("Points", pointsJSON)
                    val pointsType = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
                    val serializedPoints =
                        Gson().fromJson<ArrayList<Map<String, Any>>>(roomPointsJSON, pointsType)

                    val pointsDto = WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto.RoomPointsSerializerDto()
                    for (serializedPoint in serializedPoints) {
                        val pX = getFloat(serializedPoint["x"])
                        val pY = getFloat(serializedPoint["y"])

                        val tempPoint = WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.RoomSerializerDto.RoomPointsSerializerDto.RoomPointSerializerDto()
                        tempPoint.x = pX
                        tempPoint.y = pY

                        pointsDto.points.add(tempPoint)
                    }

                    // ---
                    roomDto.roomId = roomID
                    roomDto.roomName = roomName
                    roomDto.roomPoints = pointsDto
                    roomsDtos.add(roomDto)
                }

                // ---

                // All POIs
                val floorPoisType = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
                val serializedFloorPois = Gson().fromJson<ArrayList<Map<String, Any>>>(floorPOIsJSON, floorPoisType)

                val poiDtos = ArrayList<WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.POISerializerDto>()
                for (serializedFloorPoi in serializedFloorPois) {

                    // "poi_iD" "poi_name" "poi_type" "x" "y"
                    val tempPOI = WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto.POISerializerDto()
                    val poiId = getInt(serializedFloorPoi["poi_iD"])
                    val poiType = getInt(serializedFloorPoi["poi_type"])
                    val poiX = getFloat(serializedFloorPoi["x"])
                    val poiY = getFloat(serializedFloorPoi["y"])
                    val poiName = getString(serializedFloorPoi["poi_name"])

                    tempPOI.poiId = poiId
                    tempPOI.poiName = poiName
                    tempPOI.poiType = poiType
                    tempPOI.x = poiX
                    tempPOI.y = poiY

                    poiDtos.add(tempPOI)
                }

                // ---

                // Add Floor
                val serialFloorDto = WorldMapsSerializerDto.WorldMapSerializerDto.FloorSerializerDto()
                serialFloorDto.floorName = floorName
                serialFloorDto.floorNumber = floorNumber.toDouble().toInt()
                serialFloorDto.floorId = floorId.toDouble().toInt()
                serialFloorDto.floorRooms = roomsDtos
                serialFloorDto.floorPOIs = poiDtos

                serialFloorDtos.add(serialFloorDto)
            }

            // ---

            // Finalize MapModel
            val worldMapId = serializedWorldMapData["map_id"].toString()
            val worldMapName = serializedWorldMapData["map_name"].toString()
            val worldMapNameShort = serializedWorldMapData["map_name_short"].toString()

            val worldMapSerializerDto = WorldMapsSerializerDto.WorldMapSerializerDto()
            worldMapSerializerDto.mapId = worldMapId
            worldMapSerializerDto.mapName = worldMapName
            worldMapSerializerDto.mapNameShort = worldMapNameShort
            worldMapSerializerDto.mapDimensions = worldMapDimensionSerializerDto
            worldMapSerializerDto.mapFloors = serialFloorDtos

            val worldMapDto = worldMapSerializerDto.toWorldMapDto()

            worldMapDtos.add(worldMapDto)

            //Log.d("Map", "Adding Map model\n${model.print()}")

        }

        val worldMapsDto = ComplexWorldMapsDto(
            maps = worldMapDtos
        )

        return Result.success(worldMapsDto)
    }

    fun parseUnMinified(serializedWorldMapsDto: WorldMapsSerializerDto): Result<ComplexWorldMapsDto> {

        return Result.success(serializedWorldMapsDto.toWorldMapsDto())
    }

    private fun getString(value: Any?): String {
        return value.toString()
    }

    private fun getDouble(value: Any?): Double {
        return getString(value).toDouble()
    }

    private fun getFloat(value: Any?): Float {
        return getString(value).toFloat()
    }

    private fun getInt(value: Any?): Int {
        return getDouble(value).toInt()
    }

    private fun getJSON(`in`: Any?): String {
        return Gson().toJson(`in`)
    }
}