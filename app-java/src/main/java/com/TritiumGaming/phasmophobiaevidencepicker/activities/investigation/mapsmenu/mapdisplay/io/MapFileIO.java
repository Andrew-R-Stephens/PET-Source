package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.io.InputStream;

public class MapFileIO {

    public final WorldMapWrapper mapsWrapper = new WorldMapWrapper();

    public final MapFileReader reader = new MapFileReader(mapsWrapper);

    /** @noinspection UnusedReturnValue*/
    public boolean readFile(InputStream inputStream, MapFileReader reader) {
        return reader.loadFile(inputStream);
    }

}
