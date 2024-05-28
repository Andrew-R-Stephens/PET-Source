package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

@Keep
public class MapDesBlueprint {

    @SerializedName("maps")
    public final ArrayList<Map<String, WorldMap>> maps = new ArrayList<>();

    @Keep
    public static class WorldMap {

        @SerializedName("map_id")
        public int map_id;
        @SerializedName("map_name")
        public String map_name;
        @SerializedName("map_name_short")
        public String map_name_short;
        @SerializedName("map_floors")
        public final ArrayList<Floor> map_floors = new ArrayList<>();
        @SerializedName("map_dimensions")
        public final WorldDimensions map_dimensions = new WorldDimensions();

        @Keep
        public static class WorldDimensions {

            @SerializedName("w")
            public int w;
            @SerializedName("h")
            public int h;

            public void print() {
                Log.d("Map", "Map Dimensions:\t\tW: " + w + ", H: " + h);
            }

        }

        @Keep
        public static class Floor {

            @SerializedName("floor_id")
            public int floor_id;
            @SerializedName("floor_number")
            public int floor_number;
            @SerializedName("floor_name")
            public final String floor_name = "";
            @SerializedName("image_file")
            public final String image_file = "";
            @NonNull
            @SerializedName("floor_pois")
            public ArrayList<POI> floor_pois = new ArrayList<>();
            @NonNull
            @SerializedName("floor_rooms")
            public ArrayList<Room> floor_rooms = new ArrayList<>();

            @Keep
            public static class Room {

                @SerializedName("room_iD")
                public int room_iD;
                @NonNull
                @SerializedName("room_name")
                public String room_name = "";

                @NonNull
                @SerializedName("room_points")
                public RoomPoints room_points = new RoomPoints();

                public void print() {
                    Log.d("Maps", "\t\t" + room_iD + ": " + room_name);
                    room_points.print();
                }

                @Keep
                public static class RoomPoints {

                    @SerializedName("points")
                    public final ArrayList<RoomPoint> points = new ArrayList<>();

                    public void print() {
                        for (RoomPoint p : points) {
                            p.print();
                        }
                    }

                    @Keep
                    public static class RoomPoint {

                        @SerializedName("x")
                        public float x = 0;
                        @SerializedName("y")
                        public float y = 0;

                        public void print() {
                            Log.d("Maps", "\t\t\t\t[x: " + x + ", y: " + y + "]");
                        }
                    }

                }

            }

            @Keep
            public static class POI {

                @SerializedName("poi_iD")
                public int poi_iD;
                @NonNull
                @SerializedName("poi_name")
                public String poi_name = "";
                @SerializedName("poi_type")
                public int poi_type = -1;

                @SerializedName("x")
                public float x = 0;
                @SerializedName("y")
                public float y = 0;

                public void print() {
                    Log.d("Maps", "\t\t" + poi_iD + ": " + poi_name + ", " + poi_type + ", x: " + x + " y: " + y);
                }
            }

            public void print() {
                Log.d("Maps", "\t" + floor_number + ": " + floor_name + ", Room Count: " + floor_rooms.size() + "\n\tFloor image: " + image_file);
                for (Room r : floor_rooms) {
                   r.print();
                }

                for (POI p : floor_pois) {
                    p.print();
                }
            }

        }

        public void print() {
            Log.d("Maps", map_id + ": " + map_name + ", Floor Count: " + map_floors.size());
            map_dimensions.print();
            for (Floor f : map_floors) {
                f.print();
            }
        }
    }

    public void print() {
        for (Map<String, WorldMap> m : maps) {
            WorldMap map = m.get("map_data");
            if(map != null) {
                map.print();
            }
        }
    }
}