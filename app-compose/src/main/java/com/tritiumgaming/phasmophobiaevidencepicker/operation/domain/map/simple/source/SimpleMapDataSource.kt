package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel

interface SimpleMapDataSource {

    fun fetchMaps(context: android.content.Context): List<MapInteractModel>
    fun fetchSizeModifiers(context: android.content.Context): List<MapSizeModel>

}