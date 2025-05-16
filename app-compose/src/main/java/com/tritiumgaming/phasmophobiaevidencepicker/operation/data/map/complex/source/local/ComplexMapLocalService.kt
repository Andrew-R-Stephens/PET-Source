package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local

import android.content.res.AssetManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ComplexMapLocalService {

    fun readFile(assets: AssetManager, fileName: String): Result<WorldMaps> {
        val inputStream: InputStream? = assets.open(fileName)

        //Creating a JSONObject object
        val gson = GsonBuilder().create()

        if (inputStream == null) {
            throw Exception("InputStream is null")
        }
        val fileReader = gson.newJsonReader(BufferedReader(InputStreamReader(inputStream)))
        val type = object : TypeToken<WorldMaps?>() {}.type
        val worldMaps: WorldMaps = gson.fromJson(fileReader, type)

        try {
            fileReader.close()
            inputStream.close()
        } catch (e: IOException) { e.printStackTrace() }

        return Result.success(worldMaps)
    }

}