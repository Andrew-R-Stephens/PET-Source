package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.Floor
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.Floor.POI
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.Floor.Room
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.Floor.Room.RoomPoints
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.Floor.Room.RoomPoints.RoomPoint
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint.WorldMap.WorldDimensions
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.FloorModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.MapDimensionModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.MapModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MapDesFactory {
    fun parseMinified(
        mapDesBlueprint: MapDesBlueprint, mapModels: ArrayList<MapModel>) {

        for (mappedWorldMap in mapDesBlueprint.maps) {
            // ---

            val mappedWorldMapJSON = getJSON(mappedWorldMap)

            val mappedMapType = object : TypeToken<Map<String?, WorldMap?>?>() {}.type
            val mappedMap =
                Gson().fromJson<Map<String, WorldMap>>(mappedWorldMapJSON, mappedMapType)

            val mapJSON = getJSON(mappedMap["map_data"])
            val mapType = object : TypeToken<Map<String?, WorldMap?>?>() {}.type
            val map = Gson().fromJson<Map<String, *>>(mapJSON, mapType)

            // ---

            // Dimensions
            val dimensionJSON = getJSON(map["map_dimensions"])
            val mappedDimensionType = object : TypeToken<Map<String?, WorldDimensions?>?>() {}.type
            val mappedDimension =
                Gson().fromJson<Map<String, WorldDimensions>>(dimensionJSON, mappedDimensionType)
            val mapDimension = MapDimensionModel(
                getInt(mappedDimension["w"]),
                getInt(mappedDimension["h"])
            )

            //--

            // All Floors
            val floorsJSON = getJSON(map["map_floors"])
            val floorsType = object : TypeToken<ArrayList<Map<String?, *>?>?>() {}.type
            val mappedFloors = Gson().fromJson<ArrayList<Map<String, *>>>(floorsJSON, floorsType)

            val mapFloors = ArrayList<FloorModel>()
            for (mappedFloor in mappedFloors) {
                // Floor    "floor_id" "floor_number" "floor_pois" "floor_rooms"
                val floorJSON = getJSON(mappedFloor)
                val floorType = object : TypeToken<Map<String?, *>?>() {}.type
                val mappedFloorItem = Gson().fromJson<Map<String, *>>(floorJSON, floorType)

                val floor_id = getInt(mappedFloorItem["floor_id"])
                val floor_number = getInt(mappedFloorItem["floor_number"])
                val roomsJSON = getJSON(mappedFloorItem["floor_rooms"])
                val poisJSON = getJSON(mappedFloorItem["floor_pois"])


                // All Rooms
                val roomsType = object : TypeToken<ArrayList<Map<String?, *>?>?>() {}.type
                val mappedRooms = Gson().fromJson<ArrayList<Map<String, *>>>(roomsJSON, roomsType)

                val roomsCollection = ArrayList<Room>()
                for (mappedRoom in mappedRooms) {
                    // "room_iD" "room_name" "room_points"

                    val tempRoom = Room()
                    val room_iD = getInt(mappedRoom["room_iD"])
                    val room_name = getString(mappedRoom["room_name"])
                    val pointsListJSON = getJSON(mappedRoom["room_points"])


                    // All Points
                    //"points" "x": "y":
                    val pointsListType = object : TypeToken<Map<String?, *>?>() {}.type
                    val mappedPointsList =
                        Gson().fromJson<Map<String, *>>(pointsListJSON, pointsListType)

                    val pointsJSON = getJSON(mappedPointsList["points"])
                    val pointsType = object : TypeToken<ArrayList<Map<String?, *>?>?>() {}.type
                    val mappedPoints =
                        Gson().fromJson<ArrayList<Map<String, *>>>(pointsJSON, pointsType)

                    val pointsCollection = RoomPoints()
                    for (point in mappedPoints) {
                        val tempPoint = RoomPoint()
                        val p_x = getFloat(point["x"])
                        val p_y = getFloat(point["y"])
                        tempPoint.x = p_x
                        tempPoint.y = p_y
                        pointsCollection.points.add(tempPoint)
                    }

                    // ---
                    tempRoom.room_iD = room_iD
                    tempRoom.room_name = room_name
                    tempRoom.room_points = pointsCollection
                    roomsCollection.add(tempRoom)
                }

                // ---

                // All POIs
                val poisType = object : TypeToken<ArrayList<Map<String?, *>?>?>() {}.type
                val mappedPois = Gson().fromJson<ArrayList<Map<String, *>>>(poisJSON, poisType)

                val poisCollection = ArrayList<POI>()
                for (mappedPoi in mappedPois) {
                    // "poi_iD" "poi_name" "poi_type" "x" "y"
                    val tempPOI = POI()
                    val poi_iD = getInt(mappedPoi["poi_iD"])
                    val poi_type = getInt(mappedPoi["poi_type"])
                    val poi_x = getFloat(mappedPoi["x"])
                    val poi_y = getFloat(mappedPoi["y"])
                    val poi_name = getString(mappedPoi["poi_name"])

                    tempPOI.poi_iD = poi_iD
                    tempPOI.poi_name = poi_name
                    tempPOI.poi_type = poi_type
                    tempPOI.x = poi_x
                    tempPOI.y = poi_y
                    poisCollection.add(tempPOI)
                }

                // ---

                // Add Floor
                val floor = Floor()
                floor.floor_number = floor_number
                floor.floor_id = floor_id
                floor.floor_rooms = roomsCollection
                floor.floor_pois = poisCollection
                mapFloors.add(FloorModel(floor))
            }

            // ---

            // Finalize MapModel
            val model = MapModel()
            model.mapId = map["map_id"].toString().toDouble().toInt()
            model.mapName = (map["map_name"] as String?)!!
            model.mapNameShort = (map["map_name_short"] as String?)!!
            model.mapDimensions = mapDimension
            model.mapFloors = mapFloors

            mapModels.add(model)
        }
    }

    fun parseUnMinified(mapDesBlueprint: MapDesBlueprint, mapModels: ArrayList<MapModel>) {
        for (map in mapDesBlueprint.maps) {
            map["map_data"]?.let { worldMap -> mapModels.add(MapModel(worldMap)) }
        }
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
