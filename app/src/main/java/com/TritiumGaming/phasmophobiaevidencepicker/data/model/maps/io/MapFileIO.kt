package com.TritiumGaming.phasmophobiaevidencepicker.data.model.maps.io

import android.content.res.AssetManager
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint
import java.io.InputStream

class MapFileIO {

    private val deserializedData: MapDesBlueprint = MapDesBlueprint()

    val mapFileReader: MapFileReader = MapFileReader(deserializedData)

    fun readFile(inputStream: InputStream?, reader: MapFileReader): Boolean {
        val isSuccessful = reader.loadFile(inputStream)
        inputStream?.close()
        return isSuccessful
    }

    fun readFile(assets: AssetManager, fileName: String, reader: MapFileReader): Boolean {
        val inputStream: InputStream = assets.open(fileName)
        return readFile(inputStream, reader)
    }
}
