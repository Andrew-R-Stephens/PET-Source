package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class MapDesBlueprint {
    @SerializedName("maps") val maps: ArrayList<Map<String, WorldMap>> = ArrayList()

    @Keep
    class WorldMap {
        @SerializedName("map_id") var mapId: Int = 0
        @SerializedName("map_name") var mapName: String = ""
        @SerializedName("map_name_short") var mapNameShort: String = ""
        @SerializedName("map_floors") val mapFloors: ArrayList<Floor> = ArrayList()
        @SerializedName("map_dimensions") val mapDimensions: WorldDimensions = WorldDimensions()

        @Keep
        class WorldDimensions {
            @SerializedName("w") var w: Int = 0
            @SerializedName("h") var h: Int = 0

            fun print() {
                Log.d("Map", "Map Dimensions:\t\tW: $w, H: $h")
            }
        }

        @Keep
        class Floor {
            @SerializedName("floor_id") var floorId: Int = 0
            @SerializedName("floor_number") var floorNumber: Int = 0
            @SerializedName("floor_name") val floorName: String = ""
            @SerializedName("image_file") val imageFile: String = ""
            @SerializedName("floor_pois") var floorPOIs: ArrayList<POI> = ArrayList()
            @SerializedName("floor_rooms") var floorRooms: ArrayList<Room> = ArrayList()

            @Keep
            class Room {
                @SerializedName("room_iD") var roomId: Int = 0
                @SerializedName("room_name") var roomName: String = ""
                @SerializedName("room_points") var roomPoints: RoomPoints = RoomPoints()

                fun print() {
                    Log.d("Maps", "\t\t$roomId: $roomName")
                    roomPoints.print()
                }

                @Keep
                class RoomPoints {
                    @SerializedName("points") val points: ArrayList<RoomPoint> = ArrayList()

                    fun print() { for (p in points) { p.print() } }

                    @Keep
                    class RoomPoint {
                        @SerializedName("x") var x: Float = 0f
                        @SerializedName("y") var y: Float = 0f

                        fun print() {
                            Log.d("Maps", "\t\t\t\t[x: $x, y: $y]")
                        }
                    }
                }
            }

            @Keep
            class POI {
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
            Log.d("Maps", mapId.toString() + ": " + mapName + ", Floor Count: " + mapFloors.size)
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