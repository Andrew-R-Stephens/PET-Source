package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.io

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.io.factory.MapDesBlueprint
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MapFileReader(var worldMapDeserializer: MapDesBlueprint) {

    fun loadFile(inputStream: InputStream?): Boolean {
        //Creating a JSONObject object
        val gson = GsonBuilder().create()

        if (inputStream == null) {
            return false
        }
        val fileReader = gson.newJsonReader(BufferedReader(InputStreamReader(inputStream)))
        val type = object : TypeToken<MapDesBlueprint?>() {}.type
        worldMapDeserializer = gson.fromJson(fileReader, type)

        try {
            fileReader.close()
            inputStream.close()
        } catch (e: IOException) { e.printStackTrace() }
        return true
    }

    override fun toString(): String {
        val data = StringBuilder()

        for (m in worldMapDeserializer.maps) {
            data.append(m["map_data"])
        }
        return data.toString()
    }
}
