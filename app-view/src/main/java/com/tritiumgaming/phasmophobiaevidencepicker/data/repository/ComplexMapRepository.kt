package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.io.MapFileIO
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.map.MapListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComplexMapRepository(
    private val context: Context
) {

    var mapListModel: MapListModel? = null

    init {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                mapListModel = readMapsDataFromFile() }
        }
        catch (e: Exception) { e.printStackTrace() }
    }

    @Throws(Exception::class)
    private fun readMapsDataFromFile(): MapListModel? {
        val mapFileIO = MapFileIO()
        val reader = mapFileIO.mapFileReader

        mapFileIO.readFile(context.assets, context.getString(R.string.mapsJson), reader)
        mapListModel = MapListModel(reader.worldMapDeserializer)
        mapListModel?.orderRooms()

        return mapListModel
    }
}