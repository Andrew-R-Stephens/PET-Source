package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.io.InputStream;

public class MapFileIO {

    public WorldMapWrapper mapsWrapper = new WorldMapWrapper();

    public MapFileReader reader = new MapFileReader(mapsWrapper);

    public boolean readFile(InputStream inputStream, MapFileReader reader) {
        return reader.loadFile(inputStream);
    }

}
