package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.util.ArrayList;

public class MapModel {

    public int mapId;
    public String mapName;
    public MapDimension mapDimensions;

    public FloorLayer currentLayer = FloorLayer.values()[0];

    public ArrayList<FloorModel> mapFloors = new ArrayList<>();

    public MapModel() {
        mapId = 0;
        mapName = "undefined";
        mapDimensions = new MapDimension(0, 0);
        for(FloorLayer layer: FloorLayer.values())
            mapFloors.add(new FloorModel(layer));
    }

    public MapModel(WorldMapWrapper.WorldMap worldMap) {
        mapId = worldMap.map_id;
        mapName = worldMap.map_name;
        mapDimensions = new MapDimension(worldMap.map_dimensions.w, worldMap.map_dimensions.h);
        for(WorldMapWrapper.WorldMap.Floor f: worldMap.map_floors) {
            mapFloors.add(new FloorModel(f));
        }
        currentLayer = mapFloors.size() != 0 ? mapFloors.get(0).getFloorLayer() : null;
    }

    public MapModel(String name, int id, MapDimension dims, ArrayList<WorldMapWrapper.WorldMap.Floor> floors) {
    }

    public FloorModel getCurrentFloor() {

        for(FloorModel floor : mapFloors) {
            if(floor.getFloorLayer() == currentLayer) {
                return floor;
            }
        }
        return mapFloors.get(0);
    }

    public FloorLayer setCurrentLayer(FloorLayer layer) {

        if(hasFloorAtLayer(layer)) {
            this.currentLayer = layer;
        }

        return currentLayer;
    }

    private boolean hasFloorAtLayer(FloorLayer layer) {
        for(FloorModel floorModel : mapFloors) {
            if(floorModel.getFloorLayer() == layer) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "\n[Map ID: " + mapId + "] [Map Name: " + mapName+ "] [Dimensions: "  + mapDimensions + "] [Layer: "  + currentLayer+ "] \nFloor Data:"  + mapFloors + "\n";
    }

    public FloorModel getFloor(int index) {
        return mapFloors.get(index);
    }

    public synchronized void print() {

        Log.d("Maps", mapId + " " + mapName);
        mapDimensions.print();
        for(FloorModel f: mapFloors) {
            f.print();
        }
    }

    public void orderRooms() {
        for(FloorModel f: mapFloors) {
            f.orderRooms();
        }
    }
}
