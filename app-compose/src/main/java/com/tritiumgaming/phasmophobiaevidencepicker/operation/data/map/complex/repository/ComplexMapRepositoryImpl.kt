package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.toMapList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.MapListModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComplexMapRepositoryImpl(
    context: Context,
    override val localSource: ComplexMapDataSource
): ComplexMapRepository {

    override var worldMaps: WorldMaps = WorldMaps()
    override var mapListModel: MapListModel = MapListModel()

    override suspend fun fetchMaps(context: Context): WorldMaps {
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