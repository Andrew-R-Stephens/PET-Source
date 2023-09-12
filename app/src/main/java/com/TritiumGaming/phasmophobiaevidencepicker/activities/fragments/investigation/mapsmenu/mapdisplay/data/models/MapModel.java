package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.util.ArrayList;

public class MapModel {

    public int mapId;
    public String mapName;
    public MapDimension mapDimensions;

    public FloorLayer currentLayer = FloorLayer.values()[0];

    public ArrayList<FloorModel> mapFloors = new ArrayList<>();//new ArrayList<>(3);

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

    public boolean hasFloorById(int id) {
        for(FloorModel f: mapFloors) {
            if(f.floorId == id) {
                return true;
            }
        }
        return false;
    }

    public int getCurrentLayerOrdinal() {
        return currentLayer == null ? -1 : currentLayer.ordinal();
    }

    public String toString() {
        return "\n[Map ID: " + mapId + "] [Map Name: " + mapName+ "] [Dimensions: "  + mapDimensions + "] [Layer: "  + currentLayer+ "] \nFloor Data:"  + mapFloors + "\n";
    }

    public FloorModel getFloor(int index) {
        return mapFloors.get(index);
    }

    public FloorModel getFloor(FloorLayer layer) {
        for(FloorModel floor : mapFloors) {
            if(floor.getFloorLayer() == layer) {
                return floor;
            }
        }
        return null;
    }

    public void addFloor(FloorLayer layer) {
        FloorModel floorModel = new FloorModel(layer);
        floorModel.floorId = layer.ordinal();
        mapFloors.add(floorModel);
    }

    public boolean removeFloor(FloorModel floorModel) {
        if(floorModel != null) {
            if(mapFloors.remove(floorModel)) {
                setCurrentLayer(getFloor(0).getFloorLayer());
                return true;
            }
        }
        return false;
    }

    public void incrementLayer() {

        int ordinal = getCurrentLayerOrdinal();
        int newOrdinal = ordinal +1;
        newOrdinal = newOrdinal > getHighestFloorLayer().ordinal() ? getLowestFloorLayer().ordinal() : newOrdinal;

        FloorModel floorModel1 = getFloor(FloorLayer.values()[newOrdinal]);
        FloorModel floorModel2 = getCurrentFloor();
        swapFloorLayers(floorModel1, floorModel2);
    }

    public void decrementLayer() {

        int ordinal = getCurrentLayerOrdinal();
        int newOrdinal = ordinal -1;
        newOrdinal = newOrdinal < getLowestFloorLayer().ordinal() ? getHighestFloorLayer().ordinal() : newOrdinal;

        FloorModel floorModel1 = getCurrentFloor();
        FloorModel floorModel2 = getFloor(FloorLayer.values()[newOrdinal]);
        swapFloorLayers(floorModel1, floorModel2);
    }

    private void swapFloorLayers(FloorModel floorModel1, FloorModel floorModel2) {
        FloorLayer floorModel1Layer = floorModel1.getFloorLayer();

        floorModel1.setFloorLayer(floorModel2.getFloorLayer());
        floorModel2.setFloorLayer(floorModel1Layer);
    }

    public FloorLayer getLowestFloorLayer() {
        int lowestLayer = FloorLayer.THIRD_FLOOR.ordinal();
        for(FloorModel floor : mapFloors) {
            int newOrdinal = floor.getFloorLayer().ordinal();
            if(newOrdinal < lowestLayer) {
                lowestLayer = newOrdinal;
            }
        }
        return FloorLayer.values()[lowestLayer];
    }

    public FloorLayer getHighestFloorLayer() {
        int highestLayer = FloorLayer.BASEMENT.ordinal();
        for(FloorModel floor : mapFloors) {
            int newOrdinal = floor.getFloorLayer().ordinal();
            if(newOrdinal > highestLayer) {
                highestLayer = newOrdinal;
            }
        }
        return FloorLayer.values()[highestLayer];
    }

    public synchronized void print() {

        Log.d("Maps", mapId + " " + mapName);
        mapDimensions.print();
        for(FloorModel f: mapFloors) {
            f.print();
        }
    }

}
