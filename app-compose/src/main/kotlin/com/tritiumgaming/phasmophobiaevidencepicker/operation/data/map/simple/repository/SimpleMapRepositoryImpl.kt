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

    var simpleMaps: List<MapInteractModel> = emptyList()
    var simpleModifiers: List<MapSizeModel> = emptyList()
    @DrawableRes var simpleThumbnails: List<Int> = emptyList()

    fun sync() {
        simpleMaps = localSource.fetchMaps().getOrDefault(emptyList())
        simpleModifiers = localSource.fetchSizeModifiers().getOrDefault(emptyList())
        simpleThumbnails = simpleMaps.map { it -> it.thumbnailImage }
    }

    override fun getMaps(): Result<List<MapInteractModel>> = Result.success(simpleMaps)
    override fun getModifiers(): Result<List<MapSizeModel>> = Result.success(simpleModifiers)
    override fun getThumbnails(): Result<List<Int>> = Result.success(simpleThumbnails)

    init {
        sync()
    }

}
