package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

// TODO Fix this and implement clean code!
class SimpleMapRepositoryImpl(
    val localSource: SimpleMapDataSource
): SimpleMapRepository {

    var maps: List<MapInteractModel> = emptyList()
    var modifiers: List<MapSizeModel> = emptyList()

    val mapThumbnails: List<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            maps.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

    fun sync() {
        maps = localSource.fetchMaps().getOrDefault(emptyList())
        modifiers = localSource.fetchSizeModifiers().getOrDefault(emptyList())
    }

    override fun getMaps(): List<MapInteractModel> = maps
    override fun getModifiers(): List<MapSizeModel> = modifiers

    init {
        sync()
    }

}
