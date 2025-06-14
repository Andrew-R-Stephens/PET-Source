package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.LocalWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

class SimpleMapRepositoryImpl(
    val localSource: SimpleMapDataSource
): SimpleMapRepository {

    var simpleMaps: List<LocalWorldMap> = emptyList()
    var simpleModifiers: List<WorldMapModifier> = emptyList()
    @DrawableRes var simpleThumbnails: List<Int> = emptyList()

    fun sync() {
        simpleMaps = localSource.fetchMaps().getOrDefault(emptyList()).toDomain()
        simpleThumbnails = simpleMaps.map { it -> it.thumbnailImage }
    }

    override fun getMaps(): Result<List<LocalWorldMap>> = Result.success(simpleMaps)
    override fun getThumbnails(): Result<List<Int>> = Result.success(simpleThumbnails)

    init {
        sync()
    }

}
