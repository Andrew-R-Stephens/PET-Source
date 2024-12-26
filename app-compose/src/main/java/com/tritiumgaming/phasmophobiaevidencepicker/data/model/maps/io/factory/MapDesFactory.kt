package com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap.Floor
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap.Floor.POI
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap.Floor.Room
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap.Floor.Room.RoomPoints
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap.Floor.Room.RoomPoints.RoomPoint
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint.WorldMap.WorldDimensions
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map.FloorModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map.MapDimensionModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map.MapModel

object MapDesFactory {
    fun parseMinified(
        mapDesBlueprint: MapDesBlueprint, mapModels: ArrayList<MapModel>) {

        //Log.d("Map", "Blueprint: \n$mapDesBlueprint")

        for (mappedWorldMap in mapDesBlueprint.maps) {
            // ---

            val mappedWorldMapJSON = getJSON(mappedWorldMap)
            val mappedMapType = object : TypeToken<Map<String?, WorldMap?>?>() {}.type
            val mappedMap = Gson().fromJson<Map<String, WorldMap>>(mappedWorldMapJSON, mappedMapType)

            val mapJSON = getJSON(mappedMap["map_data"])
            //Log.d("Map", mapJSON)
            /*val mapType = object : TypeToken<Map<String, WorldMap>>() {}.type
            val map: Map<String, WorldMap> = Gson().fromJson(mapJSON, mapType)*/
            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = Gson().fromJson(mapJSON, mapType)

            // ---

            // Dimensions
            val dimensionJSON = getJSON(map["map_dimensions"])
            //Log.d("Dimension", dimensionJSON)
            val mappedDimensionType = object : TypeToken<Map<String?, Any>?>() {}.type
            val mappedDimension =
                Gson().fromJson<Map<String, WorldDimensions>>(dimensionJSON, mappedDimensionType)
            val mapDimension = MapDimensionModel(
                getInt(mappedDimension["w"]),
                getInt(mappedDimension["h"])
            )

            //--

            // All Floors
            val floorsJSON = getJSON(map["map_floors"])
            //Log.d("Floors", floorsJSON)
            val floorsType = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
            val mappedFloors = Gson().fromJson<ArrayList<Map<String?, Any>>>(floorsJSON, floorsType)

            val mapFloors = ArrayList<FloorModel>()
            for (mappedFloor in mappedFloors) {
                // Floor    "floor_id" "floor_number" "floor_pois" "floor_rooms"
                val floorJSON = getJSON(mappedFloor)
                //Log.d("Floor", floorJSON)
                val floorType = object : TypeToken<Map<String?, Any>?>() {}.type
                val mappedFloorItem = Gson().fromJson<Map<String, Any>>(floorJSON, floorType)

                val floorId = mappedFloorItem["floor_id"].toString()
                val floorName = mappedFloorItem["floor_name"].toString()
                val floorNumber = mappedFloorItem["floor_number"].toString()
                val roomsJSON = getJSON(mappedFloorItem["floor_rooms"])
                val floorPOIsJSON = getJSON(mappedFloorItem["floor_pois"])
                //Log.d("Floor","$floorId $floorName $floorNumber")


                // All Rooms
                val roomsType = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
                val mappedRooms = Gson().fromJson<ArrayList<Map<String, Any>>>(roomsJSON, roomsType)
                //Log.d("Rooms", roomsJSON)

                val roomsCollection = ArrayList<Room>()
                for (mappedRoom in mappedRooms) {
                    // "room_iD" "room_name" "room_points"

                    val tempRoom = Room()
                    val roomID = getInt(mappedRoom["room_iD"])
                    val roomName = getString(mappedRoom["room_name"])
                    val pointsListJSON = getJSON(mappedRoom["room_points"])
                    //Log.d("RoomPoints", pointsListJSON)


                    // All Points
                    //"points" "x": "y":
                    val pointsListType = object : TypeToken<Map<String?, Any>?>() {}.type
                    val mappedPointsList =
                        Gson().fromJson<Map<String, *>>(pointsListJSON, pointsListType)

                    val pointsJSON = getJSON(mappedPointsList["points"])
                    //Log.d("Points", pointsJSON)
                    val pointsType = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
                    val mappedPoints =
                        Gson().fromJson<ArrayList<Map<String, Any>>>(pointsJSON, pointsType)

                    val pointsCollection = RoomPoints()
                    for (point in mappedPoints) {
                        val tempPoint = RoomPoint()
                        val pX = getFloat(point["x"])
                        val pY = getFloat(point["y"])
                        tempPoint.x = pX
                        tempPoint.y = pY
                        pointsCollection.points.add(tempPoint)
                    }

                    // ---
                    tempRoom.roomId = roomID
                    tempRoom.roomName = roomName
                    tempRoom.roomPoints = pointsCollection
                    roomsCollection.add(tempRoom)
                }

                // ---

                // All POIs
                val poisType = object : TypeToken<ArrayList<Map<String?, Any>?>?>() {}.type
                val mappedPois = Gson().fromJson<ArrayList<Map<String, Any>>>(floorPOIsJSON, poisType)

                val poisCollection = ArrayList<POI>()
                for (mappedPoi in mappedPois) {
                    // "poi_iD" "poi_name" "poi_type" "x" "y"
                    val tempPOI = POI()
                    val poiId = getInt(mappedPoi["poi_iD"])
                    val poiType = getInt(mappedPoi["poi_type"])
                    val poiX = getFloat(mappedPoi["x"])
                    val poiY = getFloat(mappedPoi["y"])
                    val poiName = getString(mappedPoi["poi_name"])

                    tempPOI.poiId = poiId
                    tempPOI.poiName = poiName
                    tempPOI.poiType = poiType
                    tempPOI.x = poiX
                    tempPOI.y = poiY

                    poisCollection.add(tempPOI)
                }

                // ---

                // Add Floor
                val tempFloor = Floor()
                tempFloor.floorName = floorName
                tempFloor.floorNumber = floorNumber.toDouble().toInt()
                tempFloor.floorId = floorId.toDouble().toInt()
                tempFloor.floorRooms = roomsCollection
                tempFloor.floorPOIs = poisCollection

                mapFloors.add(FloorModel(tempFloor))
            }

            // ---

            // Finalize MapModel
            val model = MapModel()
            model.mapId = map["map_id"].toString().toDouble().toInt()
            model.mapName = map["map_name"].toString()
            model.mapNameShort = map["map_name_short"].toString()
            model.mapDimensions = mapDimension
            model.mapFloors = mapFloors

            //Log.d("Map", "Adding Map model\n${model.print()}")
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
