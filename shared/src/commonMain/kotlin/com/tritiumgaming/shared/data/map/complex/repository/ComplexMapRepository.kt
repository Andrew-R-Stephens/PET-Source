package com.tritiumgaming.shared.data.map.complex.repository

import com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMaps

interface ComplexMapRepository {

    suspend fun fetchMaps(): Result<ComplexWorldMaps>

}