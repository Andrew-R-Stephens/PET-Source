package com.tritiumgaming.shared.operation.domain.map.complex.repository

import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMaps

interface ComplexMapRepository {

    suspend fun fetchMaps(): Result<ComplexWorldMaps>

}