package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.toMapList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.maps.map.MapListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComplexMapRepository(
    context: Context,
    val localSource: ComplexMapLocalDataSource
) {

    var worldMaps: WorldMaps = WorldMaps()
    var mapListModel: MapListModel = MapListModel()

    suspend fun fetchMaps(context: Context): WorldMaps {
        var worldMaps = WorldMaps()
        try {
            worldMaps = localSource.fetchWorldMaps(context)
            Log.d("Maps", "Complex Maps:\n${worldMaps.maps.size}")
        }
        catch (e: Exception) { e.printStackTrace() }
        return worldMaps
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            worldMaps = fetchMaps(context)
            mapListModel = worldMaps.toMapList()
        }
    }

}