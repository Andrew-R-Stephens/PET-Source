package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.factory.MapDesBlueprint
import java.io.InputStream

class MapFileIO {

    private val deserializedData: MapDesBlueprint =
        MapDesBlueprint()

    val reader: MapFileReader =
        MapFileReader(
            deserializedData
        )

    fun readFile(inputStream: InputStream?, reader: MapFileReader): Boolean {
        return reader.loadFile(inputStream)
    }
}
