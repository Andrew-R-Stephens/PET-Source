package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.toMapList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.MapListModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource

class ComplexMapRepositoryImpl(
    override val localSource: ComplexMapDataSource
): ComplexMapRepository {

    override suspend fun fetchMaps(): MapListModel {
        var worldMaps = WorldMaps()
        try {
            worldMaps = localSource.fetchWorldMaps()
            Log.d("Maps", "Complex Maps:\n${worldMaps.maps.size}")
        }
        catch (e: Exception) { e.printStackTrace() }
        return worldMaps.toMapList()
    }

}