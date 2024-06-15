package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint;
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

    public MapDesBlueprint worldMapDeserializer;

    public MapFileReader(MapDesBlueprint mapsWrapper) {
        this.worldMapDeserializer = mapsWrapper;
    }

    public boolean loadFile(@Nullable InputStream inputStream) {
        //Creating a JSONObject object
        Gson gson = new GsonBuilder().create();

        if(inputStream == null) {
            return false;
        }
        JsonReader fileReader = gson.newJsonReader(
                new BufferedReader(
                        new InputStreamReader(inputStream)));
        Type type = new TypeToken<MapDesBlueprint>(){}.getType();
        worldMapDeserializer = gson.fromJson(fileReader, type);

        try {
            fileReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @NonNull
    public String toString() {
        StringBuilder data = new StringBuilder();

        for(Map<String, MapDesBlueprint.WorldMap> m: this.worldMapDeserializer.maps) {
            data.append(m.get("map_data"));
        }

        return data.toString();
    }

}
