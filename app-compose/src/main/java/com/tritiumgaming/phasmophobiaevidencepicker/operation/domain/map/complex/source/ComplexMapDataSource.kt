package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps

interface ComplexMapDataSource {
    val service: ComplexMapService

    @Throws(Exception::class)
    suspend fun fetchWorldMaps(): WorldMaps

}