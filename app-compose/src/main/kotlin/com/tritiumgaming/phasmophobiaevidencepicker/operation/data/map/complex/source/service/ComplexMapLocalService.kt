package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.service

import android.content.res.AssetManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.ComplexMapService
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ComplexMapLocalService: ComplexMapService {

    override fun readFile(assets: AssetManager, fileName: String): Result<WorldMapsSerializerDto> {

        val inputStream: InputStream? = assets.open(fileName)

        //Creating a JSONObject object
        val gson = GsonBuilder().create()

        inputStream ?: throw Exception("InputStream is null")

        val fileReader = gson.newJsonReader(BufferedReader(InputStreamReader(inputStream)))
        val type = object : TypeToken<WorldMapsSerializerDto?>() {}.type
        val worldMaps: WorldMapsSerializerDto = gson.fromJson(fileReader, type)

        return try {
            fileReader.close()
            inputStream.close()

            Result.success(worldMaps)
        } catch (e: IOException) {
            e.printStackTrace()

            Result.failure(Exception("Failed to fetch World Maps", e))
        }

    }

}