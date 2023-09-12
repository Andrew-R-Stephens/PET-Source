package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class WorldMapWrapper {

    public ArrayList<Map<String, WorldMap>> maps = new ArrayList<>();

    public static class WorldMap {

        public int map_id;
        public String map_name = "";
        public ArrayList<Floor> map_floors = new ArrayList<>();
        public WorldDimensions map_dimensions = new WorldDimensions();

        public static class WorldDimensions {

            public int w, h;

            public void print() {
                Log.d("Map", "Map Dimensions:\t\tW: " + w + ", H: " + h);
            }

        }

        public static class Floor {

            public int floor_id;
            public int floor_number;
            public String floor_name = "";
            public String image_file = "";
            public ArrayList<POI> floor_pois = new ArrayList<>();
            public ArrayList<Room> floor_rooms = new ArrayList<>();

            public static class Room {

                public int room_iD;
                public String room_name = "";

                public RoomPoints room_points = new RoomPoints();

                public void print() {
                    Log.d("Maps", "\t\t" + room_iD + ": " + room_name);
                    room_points.print();
                }

                public static class RoomPoints {

                    public ArrayList<RoomPoint> points = new ArrayList<>();

                    public void print() {
                        for (RoomPoint p : points) {
                            p.print();
                        }
                    }

                    public static class RoomPoint {

                        public float x = 0;
                        public float y = 0;

                        public void print() {
                            Log.d("Maps", "\t\t\t\t[x: " + x + ", y: " + y + "]");
                        }
                    }

                }

            }

            public static class POI {

                public int poi_iD;
                public String poi_name = "";
                public int poi_type = -1;

                public float x = 0;
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
            m.get("map_data").print();
        }
    }
}