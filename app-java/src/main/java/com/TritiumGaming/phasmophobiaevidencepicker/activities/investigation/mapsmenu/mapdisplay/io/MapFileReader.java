package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public class MapFileReader {

    public WorldMapWrapper mapsWrapper;

    public MapFileReader(WorldMapWrapper mapsWrapper) {
        this.mapsWrapper = mapsWrapper;
    }

    public boolean loadFile(InputStream inputStream) {
        //Creating a JSONObject object
        Gson gson = new GsonBuilder().create();

        if(inputStream == null) {
            return false;
        }
        JsonReader fileReader = gson.newJsonReader(new BufferedReader(new InputStreamReader(inputStream)));
        Type type = new TypeToken<WorldMapWrapper>(){}.getType();
        mapsWrapper = gson.fromJson(fileReader, type);

        try {
            fileReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String toString() {
        StringBuilder data = new StringBuilder();

        for(Map<String, WorldMapWrapper.WorldMap> m: this.mapsWrapper.maps) {
            data.append(m.get("map_data"));
        }

        return data.toString();
    }

}
