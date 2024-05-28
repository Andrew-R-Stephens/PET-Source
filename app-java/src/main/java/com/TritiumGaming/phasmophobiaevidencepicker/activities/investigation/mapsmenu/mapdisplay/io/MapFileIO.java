package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesBlueprint;

import java.io.InputStream;

public class MapFileIO {

    public final MapDesBlueprint deserializedData = new MapDesBlueprint();

    public final MapFileReader reader = new MapFileReader(deserializedData);

    /** @noinspection UnusedReturnValue*/
    public boolean readFile(InputStream inputStream, @NonNull MapFileReader reader) {
        return reader.loadFile(inputStream);
    }

}
