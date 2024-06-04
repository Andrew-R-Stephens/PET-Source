package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesFactory;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesBlueprint;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class MapListModel {

    public ArrayList<MapModel> mapModels = new ArrayList<>();

    public MapListModel(@NonNull MapDesBlueprint worldMapDeserializer) {
        try {
            MapDesFactory.parseMinified(worldMapDeserializer, mapModels);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            MapDesFactory.parseUnMinified(worldMapDeserializer, mapModels);
        }
    }

    public ArrayList<String> getShortenedMapNames() {
        ArrayList<String> names = new ArrayList<>(mapModels.size());
        for(MapModel mapModel: mapModels) {
            names.add(mapModel.mapNameShort);
        }

        return names;
    }

    @Nullable
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
