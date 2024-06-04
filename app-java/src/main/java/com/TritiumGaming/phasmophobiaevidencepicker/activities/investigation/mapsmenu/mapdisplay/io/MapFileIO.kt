package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesBlueprint
import java.io.InputStream

class MapFileIO {

    private val deserializedData: MapDesBlueprint = MapDesBlueprint()

    val reader: MapFileReader = MapFileReader(deserializedData)

    fun readFile(inputStream: InputStream?, reader: MapFileReader): Boolean {
        return reader.loadFile(inputStream)
    }
}
