package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

interface SimpleMapRepository {

    val localSource: SimpleMapDataSource

    var maps: List<MapInteractModel>
    var modifiers: List<MapSizeModel>

    val mapThumbnails: List<Int>

}
