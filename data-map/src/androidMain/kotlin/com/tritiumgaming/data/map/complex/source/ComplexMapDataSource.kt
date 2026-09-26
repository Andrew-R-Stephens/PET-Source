package com.tritiumgaming.data.map.complex.source

import com.tritiumgaming.data.map.complex.mappers.WorldMapsSerializerDto

interface ComplexMapDataSource {
    val service: ComplexMapService

    @Throws(Exception::class)
    suspend fun fetchWorldMaps(): Result<WorldMapsSerializerDto>

}