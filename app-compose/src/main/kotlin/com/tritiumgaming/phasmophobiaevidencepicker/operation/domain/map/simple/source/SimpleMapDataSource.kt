package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel

interface SimpleMapDataSource {

    fun fetchMaps(): Result<List<MapInteractModel>>
    fun fetchSizeModifiers(): Result<List<MapSizeModel>>

}