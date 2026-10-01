package com.tritiumgaming.data.map.complex.repository

import com.tritiumgaming.data.map.complex.model.ComplexWorldMaps

interface ComplexMapRepository {

    suspend fun fetchMaps(): Result<ComplexWorldMaps>

}