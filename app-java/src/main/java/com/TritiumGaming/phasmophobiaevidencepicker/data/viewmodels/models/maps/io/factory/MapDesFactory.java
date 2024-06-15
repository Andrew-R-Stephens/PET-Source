package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.FloorModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.MapDimensionModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.MapModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class MapDesFactory {

    public static void parseMinified(
            @NotNull MapDesBlueprint mapDesBlueprint,
            @NotNull ArrayList<MapModel> mapModels) {

        for(Map<String, MapDesBlueprint.WorldMap> mappedWorldMap: mapDesBlueprint.maps) {

            // ---
            String mappedWorldMapJSON = getJSON(mappedWorldMap);

            Type mappedMapType = new TypeToken<Map<String, MapDesBlueprint.WorldMap>>() {}.getType();
            Map<String, MapDesBlueprint.WorldMap> mappedMap = new Gson().fromJson(mappedWorldMapJSON, mappedMapType);

            String mapJSON = getJSON(mappedMap.get("map_data"));
            Type mapType = new TypeToken<Map<String, MapDesBlueprint.WorldMap>>() {}.getType();
            Map<String, ?> map = new Gson().fromJson(mapJSON, mapType);
            // ---


            // Dimensions
            String dimensionJSON = getJSON(map.get("map_dimensions"));
            Type mappedDimensionType = new TypeToken<Map<String, MapDesBlueprint.WorldMap.WorldDimensions>>() {}.getType();
            Map<String, MapDesBlueprint.WorldMap.WorldDimensions> mappedDimension = new Gson().fromJson(dimensionJSON, mappedDimensionType);
            MapDimensionModel mapDimension = new MapDimensionModel(
                    getInt(mappedDimension.get("w")),
                    getInt(mappedDimension.get("h"))
            );
            //--


            // All Floors
            String floorsJSON = getJSON(map.get("map_floors"));
            Type floorsType = new TypeToken<ArrayList<Map<String, ?>>>() {}.getType();
            ArrayList<Map<String, ?>> mappedFloors = new Gson().fromJson(floorsJSON, floorsType);

            ArrayList<FloorModel> mapFloors = new ArrayList<>();
            for (Map<String, ?> mappedFloor : mappedFloors) {
                // Floor    "floor_id" "floor_number" "floor_pois" "floor_rooms"
                String floorJSON = getJSON(mappedFloor);
                Type floorType = new TypeToken<Map<String, ?>>() {}.getType();
                Map<String, ?> mappedFloorItem = new Gson().fromJson(floorJSON, floorType);

                int floor_id = getInt(mappedFloorItem.get("floor_id"));
                int floor_number = getInt(mappedFloorItem.get("floor_number"));
                String roomsJSON = getJSON(mappedFloorItem.get("floor_rooms"));
                String poisJSON = getJSON(mappedFloorItem.get("floor_pois"));


                // All Rooms
                Type roomsType = new TypeToken<ArrayList<Map<String, ?>>>() {}.getType();
                ArrayList<Map<String, ?>> mappedRooms = new Gson().fromJson(roomsJSON, roomsType);

                ArrayList<MapDesBlueprint.WorldMap.Floor.Room> roomsCollection = new ArrayList<>();
                for (Map<String, ?> mappedRoom : mappedRooms) {

                    // "room_iD" "room_name" "room_points"
                    MapDesBlueprint.WorldMap.Floor.Room tempRoom = new MapDesBlueprint.WorldMap.Floor.Room();
                    int room_iD = getInt(mappedRoom.get("room_iD"));
                    String room_name = getString(mappedRoom.get("room_name"));
                    String pointsListJSON = getJSON(mappedRoom.get("room_points"));


                    // All Points
                    //"points" "x": "y":
                    Type pointsListType = new TypeToken<Map<String, ?>>() {}.getType();
                    Map<String, ?> mappedPointsList = new Gson().fromJson(pointsListJSON, pointsListType);

                    String pointsJSON = getJSON(mappedPointsList.get("points"));
                    Type pointsType = new TypeToken<ArrayList<Map<String, ?>>>() {}.getType();
                    ArrayList<Map<String, ?>> mappedPoints = new Gson().fromJson(pointsJSON, pointsType);

                    MapDesBlueprint.WorldMap.Floor.Room.RoomPoints pointsCollection = new MapDesBlueprint.WorldMap.Floor.Room.RoomPoints();
                    for (Map<String, ?> point : mappedPoints) {

                        MapDesBlueprint.WorldMap.Floor.Room.RoomPoints.RoomPoint tempPoint = new MapDesBlueprint.WorldMap.Floor.Room.RoomPoints.RoomPoint();
                        float p_x = getFloat(point.get("x"));
                        float p_y = getFloat(point.get("y"));
                        tempPoint.x = p_x;
                        tempPoint.y = p_y;
                        pointsCollection.points.add(tempPoint);
                    }
                    // ---

                    tempRoom.room_iD = room_iD;
                    tempRoom.room_name = room_name;
                    tempRoom.room_points = pointsCollection;
                    roomsCollection.add(tempRoom);
                }
                // ---

                // All POIs
                Type poisType = new TypeToken<ArrayList<Map<String, ?>>>() {}.getType();
                ArrayList<Map<String, ?>> mappedPois = new Gson().fromJson(poisJSON, poisType);

                ArrayList<MapDesBlueprint.WorldMap.Floor.POI> poisCollection = new ArrayList<>();
                for (Map<String, ?> mappedPoi : mappedPois) {
                    // "poi_iD" "poi_name" "poi_type" "x" "y"
                    MapDesBlueprint.WorldMap.Floor.POI tempPOI = new MapDesBlueprint.WorldMap.Floor.POI();
                    int poi_iD = getInt(mappedPoi.get("poi_iD"));
                    int poi_type = getInt(mappedPoi.get("poi_type"));
                    float poi_x = getFloat(mappedPoi.get("x"));
                    float poi_y = getFloat(mappedPoi.get("y"));
                    String poi_name = getString(mappedPoi.get("poi_name"));

                    tempPOI.poi_iD = poi_iD;
                    tempPOI.poi_name = poi_name;
                    tempPOI.poi_type = poi_type;
                    tempPOI.x = poi_x;
                    tempPOI.y = poi_y;
                    poisCollection.add(tempPOI);
                }
                // ---


                // Add Floor
                MapDesBlueprint.WorldMap.Floor floor = new MapDesBlueprint.WorldMap.Floor();
                floor.floor_number = floor_number;
                floor.floor_id = floor_id;
                floor.floor_rooms = roomsCollection;
                floor.floor_pois = poisCollection;
                mapFloors.add(new FloorModel(floor));
            }
            // ---


            // Finalize MapModel
            MapModel model = new MapModel();
            model.setMapId((int) Double.parseDouble(String.valueOf(map.get("map_id"))));
            model.setMapName((String) map.get("map_name"));
            model.setMapNameShort((String) map.get("map_name_short"));
            model.setMapDimensions(mapDimension);
            model.setMapFloors(mapFloors);

            mapModels.add(model);

        }
    }

    public static void parseUnMinified(
            @NotNull MapDesBlueprint mapDesBlueprint,
            @NotNull ArrayList<MapModel> mapModels) {

        for(Map<String, MapDesBlueprint.WorldMap> wm: mapDesBlueprint.maps) {
            MapDesBlueprint.WorldMap worldMap = wm.get("map_data");
            if(worldMap != null) {
                mapModels.add(new MapModel(worldMap));
            }
        }
    }

    @NonNull
    private static String getString(Object value) {
        return String.valueOf(value);
    }

    private static double getDouble(Object value) {
        return Double.parseDouble(getString(value));
    }

    private static float getFloat(Object value) {
        return Float.parseFloat(getString(value));
    }

    private static int getInt(Object value) {
        return (int)getDouble(value);
    }

    private static String getJSON(Object in) {
        return new Gson().toJson(in);
    }

}
