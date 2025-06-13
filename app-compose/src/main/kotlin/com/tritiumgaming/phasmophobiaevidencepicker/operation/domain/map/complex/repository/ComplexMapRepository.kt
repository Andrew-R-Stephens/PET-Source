package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.WorldMaps

interface ComplexMapRepository {

    suspend fun fetchMaps(): Result<WorldMaps>

}