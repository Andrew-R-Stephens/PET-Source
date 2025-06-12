package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.WorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource

interface ComplexMapRepository {
    val localSource: ComplexMapDataSource

    suspend fun fetchMaps(): Result<WorldMaps>

}