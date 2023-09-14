package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.util.ArrayList;
import java.util.Map;

public class MapListModel {

    private int currentMapID = 0;
    public ArrayList<MapModel> mapModels;

    public MapListModel(WorldMapWrapper worldMapWrapper) {
        mapModels = new ArrayList<>();

        for(Map<String, WorldMapWrapper.WorldMap> wm: worldMapWrapper.maps) {
            WorldMapWrapper.WorldMap worldMap = wm.get("map_data");
            if(worldMap != null) {
                mapModels.add(new MapModel(worldMap));
            }
        }
    }

    public MapModel getMapById(int id) {
        for(MapModel m: mapModels) {
            if(m.mapId == id) {
                return m;
            }
        }

        return null;
    }

    public MapModel getCurrentMap() {
        if(currentMapID >= mapModels.size())
            return new MapModel();

        return mapModels.get(currentMapID);
    }

    public void setCurrentMap(int selectedIndex) {
        if(selectedIndex >= mapModels.size()) {
            selectedIndex = Math.max(mapModels.size()-1, 0);
        }

        currentMapID = selectedIndex;
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
