package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

class SimpleMapRepositoryImpl(
    override val localSource: SimpleMapDataSource
): SimpleMapRepository {

    override var maps: List<MapInteractModel> = localSource.fetchMaps()
    override var modifiers: List<MapSizeModel> = localSource.fetchSizeModifiers()

    override val mapThumbnails: List<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            maps.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

}
