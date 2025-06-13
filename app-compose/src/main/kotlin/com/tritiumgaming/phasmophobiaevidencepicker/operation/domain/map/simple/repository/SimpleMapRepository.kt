package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel

interface SimpleMapRepository {

    fun getMaps(): Result<List<MapInteractModel>>
    fun getModifiers(): Result<List<MapSizeModel>>
    fun getThumbnails(): Result<List<Int>>

}
