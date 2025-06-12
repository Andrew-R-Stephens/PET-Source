package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

interface SimpleMapRepository {

    fun getMaps(): List<MapInteractModel>
    fun getModifiers(): List<MapSizeModel>
    @DrawableRes fun getThumbnails(): List<Int>

}
