package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.MapListModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource

interface ComplexMapRepository {

    val localSource: ComplexMapDataSource
    var worldMaps: WorldMaps
    var mapListModel: MapListModel

    suspend fun fetchMaps(context: Context): WorldMaps

}