package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto

interface ComplexMapDataSource {
    val service: ComplexMapService

    @Throws(Exception::class)
    suspend fun fetchWorldMaps(): Result<WorldMapsSerializerDto>

}