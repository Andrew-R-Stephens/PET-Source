package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class MapListModel {

    private int currentMapID = 0;
    public ArrayList<MapModel> mapModels;

    public MapListModel(WorldMapWrapper worldMapWrapper) {
        try {
            parseMinified(worldMapWrapper);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            parseUnMinified(worldMapWrapper);
        }
    }

    public void parseMinified(WorldMapWrapper worldMapWrapper) {
        mapModels = new ArrayList<>();

        for(Map<String, WorldMapWrapper.WorldMap> mappedWorldMap: worldMapWrapper.maps) {

            // ---
            String mappedWorldMapJSON = getJSON(mappedWorldMap);

            Type mappedMapType = new TypeToken<Map<String, WorldMapWrapper.WorldMap>>() {
            }.getType();
            Map<String, WorldMapWrapper.WorldMap> mappedMap = new Gson().fromJson(mappedWorldMapJSON, mappedMapType);

            String mapJSON = getJSON(mappedMap.get("map_data"));
            Type mapType = new TypeToken<Map<String, WorldMapWrapper.WorldMap>>() {
            }.getType();
            Map<String, ?> map = new Gson().fromJson(mapJSON, mapType);
            // ---


            // Dimensions
            String dimensionJSON = getJSON(map.get("map_dimensions"));
            Type mappedDimensionType = new TypeToken<Map<String, WorldMapWrapper.WorldMap.WorldDimensions>>() {
            }.getType();
            Map<String, WorldMapWrapper.WorldMap.WorldDimensions> mappedDimension = new Gson().fromJson(dimensionJSON, mappedDimensionType);
            MapDimension mapDimension = new MapDimension(
                    getInt(mappedDimension.get("w")),
                    getInt(mappedDimension.get("h"))
            );
            //--


            // All Floors
            String floorsJSON = getJSON(map.get("map_floors"));
            Type floorsType = new TypeToken<ArrayList<Map<String, ?>>>() {
            }.getType();
            ArrayList<Map<String, ?>> mappedFloors = new Gson().fromJson(floorsJSON, floorsType);

            ArrayList<FloorModel> mapFloors = new ArrayList<>();
            for (Map<String, ?> mappedFloor : mappedFloors) {
                // Floor    "floor_id" "floor_number" "floor_pois" "floor_rooms"
                String floorJSON = getJSON(mappedFloor);
                Type floorType = new TypeToken<Map<String, ?>>() {
                }.getType();
                Map<String, ?> mappedFloorItem = new Gson().fromJson(floorJSON, floorType);

                int floor_id = getInt(mappedFloorItem.get("floor_id"));
                int floor_number = getInt(mappedFloorItem.get("floor_number"));
                String roomsJSON = getJSON(mappedFloorItem.get("floor_rooms"));
                String poisJSON = getJSON(mappedFloorItem.get("floor_pois"));


                // All Rooms
                Type roomsType = new TypeToken<ArrayList<Map<String, ?>>>() {
                }.getType();
                ArrayList<Map<String, ?>> mappedRooms = new Gson().fromJson(roomsJSON, roomsType);

                ArrayList<WorldMapWrapper.WorldMap.Floor.Room> roomsCollection = new ArrayList<>();
                for (Map<String, ?> mappedRoom : mappedRooms) {

                    // "room_iD" "room_name" "room_points"
                    WorldMapWrapper.WorldMap.Floor.Room tempRoom = new WorldMapWrapper.WorldMap.Floor.Room();
                    int room_iD = getInt(mappedRoom.get("room_iD"));
                    String room_name = getString(mappedRoom.get("room_name"));
                    String pointsListJSON = getJSON(mappedRoom.get("room_points"));


                    // All Points
                    //"points" "x": "y":
                    Type pointsListType = new TypeToken<Map<String, ?>>() {
                    }.getType();
                    Map<String, ?> mappedPointsList = new Gson().fromJson(pointsListJSON, pointsListType);

                    String pointsJSON = getJSON(mappedPointsList.get("points"));
                    Type pointsType = new TypeToken<ArrayList<Map<String, ?>>>() {
                    }.getType();
                    ArrayList<Map<String, ?>> mappedPoints = new Gson().fromJson(pointsJSON, pointsType);

                    WorldMapWrapper.WorldMap.Floor.Room.RoomPoints pointsCollection = new WorldMapWrapper.WorldMap.Floor.Room.RoomPoints();
                    for (Map<String, ?> point : mappedPoints) {

                        WorldMapWrapper.WorldMap.Floor.Room.RoomPoints.RoomPoint tempPoint = new WorldMapWrapper.WorldMap.Floor.Room.RoomPoints.RoomPoint();
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
                Type poisType = new TypeToken<ArrayList<Map<String, ?>>>() {
                }.getType();
                ArrayList<Map<String, ?>> mappedPois = new Gson().fromJson(poisJSON, poisType);

                ArrayList<WorldMapWrapper.WorldMap.Floor.POI> poisCollection = new ArrayList<>();
                for (Map<String, ?> mappedPoi : mappedPois) {
                    // "poi_iD" "poi_name" "poi_type" "x" "y"
                    WorldMapWrapper.WorldMap.Floor.POI tempPOI = new WorldMapWrapper.WorldMap.Floor.POI();
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
                WorldMapWrapper.WorldMap.Floor floor = new WorldMapWrapper.WorldMap.Floor();
                floor.floor_number = floor_number;
                floor.floor_id = floor_id;
                floor.floor_rooms = roomsCollection;
                floor.floor_pois = poisCollection;
                mapFloors.add(new FloorModel(floor));
            }
            // ---


            // Finalize MapModel
            MapModel model = new MapModel();
            model.mapId = (int) Double.parseDouble(String.valueOf(map.get("map_id")));
            model.mapName = (String) map.get("map_name");
            model.mapDimensions = mapDimension;
            model.mapFloors = mapFloors;

            mapModels.add(model);

        }
    }

    public void parseUnMinified(WorldMapWrapper worldMapWrapper) {
        mapModels = new ArrayList<>();

        for(Map<String, WorldMapWrapper.WorldMap> wm: worldMapWrapper.maps) {
            WorldMapWrapper.WorldMap worldMap = wm.get("map_data");
            if(worldMap != null) {
                mapModels.add(new MapModel(worldMap));
            }
        }
    }

    private String getString(Object value) {
        return String.valueOf(value);
    }

    private double getDouble(Object value) {
        return Double.parseDouble(getString(value));
    }

    private float getFloat(Object value) {
        return Float.parseFloat(getString(value));
    }

    private int getInt(Object value) {
        return (int)getDouble(value);
    }

    private static String getJSON(Object in) {
        return new Gson().toJson(in);
    }

    public MapModel getMapById(int id) {
        for(MapModel m: mapModels) {
            if(m.mapId == id) {
                return m;
            }
        }

        return null;
    }

    public synchronized void print() {
        for (MapModel m : mapModels) {
            m.print();
        }
    }

    public void orderRooms() {
        for(MapModel m: mapModels) {
            m.orderRooms();
        }
    }
}
