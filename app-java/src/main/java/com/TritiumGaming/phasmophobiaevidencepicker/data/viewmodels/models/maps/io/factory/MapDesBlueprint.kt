package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class MapDesBlueprint {
    @SerializedName("maps") val maps: ArrayList<Map<String, WorldMap>> = ArrayList()

    @Keep
    class WorldMap {
        @SerializedName("map_id") var map_id: Int = 0
        @SerializedName("map_name") var map_name: String = ""
        @SerializedName("map_name_short") var map_name_short: String = ""
        @SerializedName("map_floors") val map_floors: ArrayList<Floor> = ArrayList()
        @SerializedName("map_dimensions") val map_dimensions: WorldDimensions = WorldDimensions()

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
            @SerializedName("floor_id") var floor_id: Int = 0
            @SerializedName("floor_number") var floor_number: Int = 0
            @SerializedName("floor_name") val floor_name: String = ""
            @SerializedName("image_file") val image_file: String = ""
            @SerializedName("floor_pois") var floor_pois: ArrayList<POI> = ArrayList()
            @SerializedName("floor_rooms") var floor_rooms: ArrayList<Room> = ArrayList()

            @Keep
            class Room {
                @SerializedName("room_iD") var room_iD: Int = 0
                @SerializedName("room_name") var room_name: String = ""
                @SerializedName("room_points") var room_points: RoomPoints = RoomPoints()

                fun print() {
                    Log.d("Maps", "\t\t$room_iD: $room_name")
                    room_points.print()
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
                @SerializedName("poi_iD") var poi_iD: Int = 0
                @SerializedName("poi_name") var poi_name: String = ""
                @SerializedName("poi_type") var poi_type: Int = -1
                @SerializedName("x") var x: Float = 0f
                @SerializedName("y") var y: Float = 0f

                fun print() {
                    Log.d("Maps", "\t\t$poi_iD: $poi_name, $poi_type, x: $x y: $y") }
            }

            fun print() {
                Log.d("Maps",
                    "\t$floor_number: $floor_name Room Count: $floor_rooms.size" +
                            "\n\tFloor image: $image_file")
                for (r in floor_rooms) { r.print() }
                for (p in floor_pois) { p.print() }
            }
        }

        fun print() {
            Log.d("Maps", map_id.toString() + ": " + map_name + ", Floor Count: " + map_floors.size)
            map_dimensions.print()
            for (f in map_floors) { f.print() }
        }
    }

    fun print() {
        for (m in maps) {
            val map = m["map_data"]
            map?.print()
        }
    }
}